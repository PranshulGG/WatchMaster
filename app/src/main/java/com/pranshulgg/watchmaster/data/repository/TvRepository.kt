package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.local.entity.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toEntity
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.core.network.TvSeasonEpisodeDto
import com.pranshulgg.watchmaster.core.network.TvSeasonEpisodesResponse
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
    private val episodeDao: TvEpisodeDao
) {

    suspend fun getWholeTvData(tvId: Long): TvBundle {

        dao.getById(tvId)?.let {
            return it.toDomain()
        }

        val response = api.getWholeTvData(tvId)
        val body = response.body() ?: error("TV season not found")

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

    suspend fun markEpWatched(epId: Long) {
        episodeDao.updateEpisodeStatus(epId, true)
    }

    suspend fun markEpUnWatched(epId: Long) {
        episodeDao.updateEpisodeStatus(epId, false)
    }

    fun getEpisodesForSeason(
        tvId: Long,
        seasonId: Long,
        seasonNumber: Int
    ): Flow<List<TvEpisodeEntity>> = flow {

        val count = episodeDao.countEpisodes(seasonId)

        if (count == 0) {
            fetchEpisodes(tvId, seasonId, seasonNumber)
        }

        emitAll(episodeDao.getById(seasonId))
    }


}
