package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.data.local.dao.SeasonDao
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.feature.search.SearchItem
import kotlinx.coroutines.flow.Flow
import java.time.Instant

class WatchlistRepository(
    private val dao: WatchlistDao,
    private val seasonDao: SeasonDao,
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository? = null
) {

    suspend fun addFromSearch(item: SearchItem, tvDetails: List<TvSeasonDto>? = null) {
        dao.insert(
            WatchlistItemEntity(
                id = item.id,
                mediaType = item.mediaType,
                title = item.title,
                overview = item.overview,
                posterPath = item.posterPath,
                genreIds = item.genreIds,
                releaseDate = item.releaseDate,
                addedDate = Instant.now(),
                avgRating = item.avg_rating,
                backdropPath = item.backdropPath,
            )
        )
    }

    suspend fun insertSeason(showId: Long, tvDetails: List<TvSeasonDto>) {
        val seasons = tvDetails.map {
            SeasonEntity(
                seasonNumber = it.season_number,
                name = it.name,
                episodeCount = it.episode_count,
                airDate = it.air_date,
                posterPath = it.poster_path,
                showId = showId,
                seasonAddedDate = Instant.now(),
                seasonAvgRating = it.vote_average
            )
        }

        seasonDao.insertSeasons(seasons)
    }


    fun getWatchlist() = dao.getAll()

    suspend fun markStarted(id: Long) {
        dao.updateStatus(
            id = id,
            status = WatchStatus.WATCHING,
            started = Instant.now()
        )
    }

    suspend fun markFinished(id: Long) {
        dao.updateStatus(
            id = id,
            status = WatchStatus.FINISHED,
            finished = Instant.now()
        )
    }

    suspend fun markInterrupted(id: Long) {
        dao.updateStatus(
            id = id,
            status = WatchStatus.INTERRUPTED,
            interruptedAt = Instant.now()
        )
    }

    suspend fun markWantToWatch(id: Long) {
        dao.updateStatus(
            id = id,
            status = WatchStatus.WANT_TO_WATCH
        )
    }


    suspend fun deleteItem(id: Long) {
        dao.deleteById(id)
        movieRepository.deleteCachedMovie(id)
        tvRepository?.deleteCachedTv(id)
    }

    suspend fun deleteFinishedItems() = dao.deleteFinished()

    suspend fun itemExists(id: Long): Boolean = dao.exists(id)

    suspend fun updateFavorite(id: Long, isFavorite: Boolean) = dao.updateFavorite(id, isFavorite)

    suspend fun updatePinned(id: Long, isPinned: Boolean) = dao.updatePinned(id, isPinned)

    suspend fun updateUserRating(id: Long, rating: Double) = dao.updateUserRating(id, rating)

    suspend fun updateNote(id: Long, note: String) = dao.setUserNote(id, note)

    fun getItemById(id: Long): Flow<WatchlistItemEntity?> {
        return dao.getById(id)
    }

    fun getSeasonsForShow(id: Long): Flow<List<SeasonEntity>> {
        return seasonDao.getSeasonForShow(id)
    }
    
    suspend fun markSeasonStarted(id: Long) {
        seasonDao.updateSeasonStatus(
            id = id,
            status = WatchStatus.WATCHING,
            started = Instant.now()
        )
    }

    suspend fun markSeasonFinished(id: Long) {
        seasonDao.updateSeasonStatus(
            id = id,
            status = WatchStatus.FINISHED,
            finished = Instant.now()
        )
    }

    suspend fun markSeasonInterrupted(id: Long) {
        seasonDao.updateSeasonStatus(
            id = id,
            status = WatchStatus.INTERRUPTED,
            interruptedAt = Instant.now()
        )
    }

    suspend fun markSeasonWantToWatch(id: Long) {
        seasonDao.updateSeasonStatus(
            id = id,
            status = WatchStatus.WANT_TO_WATCH
        )
    }

    suspend fun updateSeasonUserRating(id: Long, rating: Double) =
        seasonDao.updateSeasonUserRating(id, rating)

    suspend fun updateSeasonNote(id: Long, note: String) = seasonDao.setSeasonUserNote(id, note)

    suspend fun deleteSeason(id: Long) = seasonDao.deleteForShow(id)
}
