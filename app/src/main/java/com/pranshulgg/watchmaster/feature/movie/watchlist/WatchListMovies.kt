package com.pranshulgg.watchmaster.feature.movie.watchlist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.components.MovieItems

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WatchlistMovies(
    isLoading: Boolean = true,
    items: List<WatchlistItemEntity>,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
    navController: NavController,
    onLongActionMovieRequest: (WatchlistItemEntity) -> Unit
) {

    if (isLoading) {
        LoadingScreenPlaceholder()
    }

    if (items.isEmpty()) {
        EmptyContainerPlaceholder(
            R.drawable.movie_info_24px,
            "No movies found",
            description = "Your watchlist is empty. Add some movies to start building it!"
        )
    }


    val pinnedItems = items.filter { it.isPinned }
    val normalItems = items.filter { !it.isPinned }


    MovieItems(
        scrollBehavior,
        scrollBehaviorTopBar,
        navController,
        onLongActionMovieRequest,
        pinnedItems,
        normalItems
    )
}
