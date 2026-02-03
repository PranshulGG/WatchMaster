package com.pranshulgg.watchmaster.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.screens.search.SearchItem
import kotlinx.coroutines.flow.Flow
import java.time.Instant

class WatchlistRepository(
    private val dao: WatchlistDao,
    private val movieRepository: MovieRepository
) {

    suspend fun addFromSearch(item: SearchItem) {
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
                avgRating = item.avg_rating
            )
        )
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
    }

    suspend fun deleteFinishedItems() = dao.deleteFinished()

    suspend fun itemExists(id: Long): Boolean = dao.exists(id)

    suspend fun updateFavorite(id: Long, isFavorite: Boolean) = dao.updateFavorite(id, isFavorite)

    suspend fun updatePinned(id: Long, isPinned: Boolean) = dao.updatePinned(id, isPinned)

    suspend fun updateUserRating(id: Long, rating: Double) = dao.updateUserRating(id, rating)

    fun getItemById(id: Long): Flow<WatchlistItemEntity?> {
        return dao.getById(id)
    }

}
