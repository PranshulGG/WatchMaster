package com.pranshulgg.watchmaster.data.repository

import android.util.Log
import androidx.room.Transaction
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.local.entity.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toEntity
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.core.network.TvSeasonEpisodeDto
import com.pranshulgg.watchmaster.core.network.TvSeasonEpisodesResponse
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.dao.SeasonDao
import com.pranshulgg.watchmaster.data.local.dao.TvEpisodeDao
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.time.Instant

class TvRepository(
    private val api: TmdbApi,
    private val dao: TvBundleDao,
    private val episodeDao: TvEpisodeDao,
    private val seasonDao: SeasonDao
) {

    suspend fun getWholeTvData(tvId: Long): TvBundle {

        dao.getById(tvId)?.let {
            return it.toDomain()
        }

        val response = api.getWholeTvData(tvId)

        val body = response.body() ?: throw Exception("TV not found")

        val domain = body.toDomain()
        dao.insert(domain.toEntity())


        return domain
    }

    suspend fun deleteCachedTv(id: Long) {
        dao.deleteById(id)
    }


    suspend fun fetchEpisodes(
        tvId: Long,
        seasonId: Long,
        seasonNumber: Int
    ) {

        val response = api.getTvSeasonEpisodes(tvId, seasonNumber)

        val body = response.body() ?: error("TV episodes not found")

        val domain = body.episodes


        val eps = domain.map { episode ->
            TvEpisodeEntity(
                seasonId = seasonId,
                showId = tvId,
                air_date = episode.air_date,
                episode_number = episode.episode_number,
                name = episode.name,
                overview = episode.overview,
                still_path = episode.still_path,
                runtime = episode.runtime,
                isWatched = episode.isWatched,
            )
        }


        episodeDao.insertEpisodes(eps)

    }

    @Transaction
    suspend fun markEpWatched(
        epId: Long,
        seasonId: Long,
        episodeNumber: Int
    ) {
        episodeDao.updateEpisodeStatus(epId, true)
        seasonDao.updateLastEpWatched(seasonId, episodeNumber)
    }

    @Transaction
    suspend fun markEpUnWatched(
        epId: Long,
        seasonId: Long,
        episodeNumber: Int
    ) {
        episodeDao.updateEpisodeStatus(epId, false)
        seasonDao.updateLastEpWatched(seasonId, episodeNumber.minus(1))
    }


    suspend fun updateLastEpWatched(seasonId: Long, epNumber: Int) {
        seasonDao.updateLastEpWatched(seasonId, epNumber)
    }

    suspend fun markAllEpWatched(seasonId: Long, lastEpNumber: Int) {
        episodeDao.markAllEpWatched(seasonId)
        seasonDao.updateLastEpWatched(seasonId, lastEpNumber)
    }

    suspend fun markAllEpUnWatched(seasonId: Long) {
        episodeDao.markAllEpUnWatched(seasonId)
        seasonDao.updateLastEpWatched(seasonId, null)
    }

    private val loadingSeasons = mutableSetOf<Long>()

    suspend fun ensureEpisodesFetched(
        tvId: Long,
        seasonId: Long,
        seasonNumber: Int,
    ) {
        if (!episodeDao.hasEpisodes(seasonId) && !loadingSeasons.contains(seasonId)) {
            loadingSeasons.add(seasonId)
            fetchEpisodes(tvId, seasonId, seasonNumber)
            loadingSeasons.remove(seasonId)
        } else {
            Log.d("TvRepository", "Episodes already fetched for season $seasonId")
        }
    }


    fun getEpisodesForSeason(seasonId: Long): Flow<List<TvEpisodeEntity>> {
        return episodeDao.getEpisodesForSeason(seasonId)
    }

    suspend fun markEpWatchedFromCount(seasonId: Long, count: Int) {
        episodeDao.markEpWatchedFromCount(seasonId, count)
    }

    suspend fun refreshSeasonData(season: SeasonEntity): SeasonEntity {

        val response = api.getTvSeasons(season.showId)

        val body = response.body() ?: throw Exception("Response body is null")

        val filteredSeason = body.seasons.firstOrNull { it.season_number == season.seasonNumber }

        val seasonEntity = SeasonEntity(
            seasonId = season.seasonId,
            showId = season.showId,
            seasonNumber = season.seasonNumber,
            name = season.name,
            episodeCount = filteredSeason?.episode_count ?: season.episodeCount,
            airDate = season.airDate,
            posterPath = season.posterPath,
            seasonAddedDate = season.seasonAddedDate,
            seasonStartedDate = season.seasonStartedDate,
            seasonInterruptedAt = season.seasonInterruptedAt,
            seasonFinishedDate = season.seasonFinishedDate,
            seasonNotes = season.seasonNotes,
            seasonAvgRating = season.seasonAvgRating,
            seasonUserRating = season.seasonUserRating,
            status = season.status,
            lastEpWatched = season.lastEpWatched,
            cachedAt = System.currentTimeMillis()
        )

        seasonDao.insertSeason(seasonEntity)

        return seasonEntity
    }

}
