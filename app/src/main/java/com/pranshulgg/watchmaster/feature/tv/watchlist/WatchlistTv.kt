package com.pranshulgg.watchmaster.feature.tv.watchlist

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.mapper.SeasonDataMapper
import com.pranshulgg.watchmaster.feature.movie.components.MovieItems
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.components.TvItems

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WatchlistTv(
    isLoading: Boolean,
    items: List<WatchlistItemEntity>,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
    navController: NavController,
    onLongActionTvRequest: (SeasonEntity, WatchlistItemEntity) -> Unit,
    viewModel: WatchlistViewModel,
    seasons: List<SeasonEntity>
) {

    val seasonShowIds = remember(seasons) {
        seasons.map { it.showId }.toSet()
    }

    val filteredItems = remember(items, seasonShowIds) {
        items.filter { it.id in seasonShowIds }
    }

    if (isLoading) {
        LoadingScreenPlaceholder()
        return
    }

    if (filteredItems.isEmpty()) {
        EmptyContainerPlaceholder(
            R.drawable.movie_info_24px,
            "No series found",
            description = "Your watchlist is empty. Add some tv series to start building it!"
        )
        return
    }




    val (pinnedItems, normalItems) = remember(filteredItems) {
        filteredItems.partition { it.isPinned }
    }



    TvItems(
        scrollBehavior,
        scrollBehaviorTopBar,
        navController,
        onLongActionTvRequest,
        pinnedItems,
        normalItems,
        viewModel,
        seasons

    )

}