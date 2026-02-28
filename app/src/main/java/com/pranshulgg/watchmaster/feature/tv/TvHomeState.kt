package com.pranshulgg.watchmaster.feature.tv

import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity

data class TvHomeState(
    val watchlist: List<WatchlistItemEntity> = emptyList(),
    val watching: List<WatchlistItemEntity> = emptyList(),
    val finished: List<WatchlistItemEntity> = emptyList(),
    val isLoading: Boolean = false,
)

fun TvHomeState(items: List<WatchlistItemEntity>, isLoading: Boolean): TvHomeState {
    return TvHomeState(
        watchlist = items.filter { it.status == WatchStatus.WANT_TO_WATCH && it.mediaType != "movie" },
        watching = items.filter { (it.status == WatchStatus.WATCHING || it.status == WatchStatus.INTERRUPTED) && it.mediaType != "movie" },
        finished = items.filter { it.status == WatchStatus.FINISHED && it.mediaType != "movie" },
        isLoading = isLoading
    )
}