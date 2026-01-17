package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.screens.search.SearchItem

class WatchlistRepository(
    private val dao: WatchlistDao
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
                releaseDate = item.releaseDate
            )
        )
    }

    fun getWatchlist() = dao.getAll()
}
