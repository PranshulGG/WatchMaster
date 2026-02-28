package com.pranshulgg.watchmaster.feature.tv.watchlist

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.mapper.SeasonDataMapper
import com.pranshulgg.watchmaster.feature.movie.components.MovieItems
import com.pranshulgg.watchmaster.feature.tv.components.TvItems

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WatchlistTv(
    isLoading: Boolean,
    items: List<WatchlistItemEntity>,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
    navController: NavController,
    onLongActionTvRequest: (WatchlistItemEntity) -> Unit
) {


    if (isLoading) {
        LoadingScreenPlaceholder()
    }

    if (items.isEmpty()) {
        EmptyContainerPlaceholder(
            R.drawable.movie_info_24px,
            "No series found",
            description = "Your watchlist is empty. Add some tv series to start building it!"
        )
    }


    val pinnedItems = items.filter { it.isPinned }
    val normalItems = items.filter { !it.isPinned }


    TvItems(
        scrollBehavior,
        scrollBehaviorTopBar,
        navController,
        onLongActionTvRequest,
        pinnedItems,
        normalItems
    )

}