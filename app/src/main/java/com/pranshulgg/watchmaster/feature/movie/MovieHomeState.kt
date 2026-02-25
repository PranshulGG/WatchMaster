package com.pranshulgg.watchmaster.feature.movie

import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.core.model.WatchStatus

data class MovieHomeState(
    val watchlist: List<WatchlistItemEntity> = emptyList(),
    val watching: List<WatchlistItemEntity> = emptyList(),
    val finished: List<WatchlistItemEntity> = emptyList(),
    val isLoading: Boolean = false
)

fun MovieHomeState(
    items: List<WatchlistItemEntity>,
    isLoading: Boolean
): MovieHomeState {
    return MovieHomeState(
        watchlist = items.filter {
            it.status == WatchStatus.WANT_TO_WATCH && it.mediaType != "tv"
        },
        watching = items.filter {
            (it.status == WatchStatus.WATCHING ||
                    it.status == WatchStatus.INTERRUPTED) &&
                    it.mediaType != "tv"
        },
        finished = items.filter {
            it.status == WatchStatus.FINISHED &&
                    it.mediaType != "tv"
        },
        isLoading = isLoading
    )
}