package com.pranshulgg.watchmaster.feature.movie.lists.create.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity

@Composable
fun MovieListsSheetContent(
    items: List<WatchlistItemEntity>,
    onMovieSelect: (WatchlistItemEntity) -> Unit,
    onMovieRemove: (WatchlistItemEntity) -> Unit
) {

    LazyColumn() {
        itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
            MovieListsSelectableItem(item, index, items, onMovieSelect, onMovieRemove)
        }
    }

}