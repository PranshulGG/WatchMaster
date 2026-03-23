package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@Composable
fun MovieListsScaffold(
    navController: NavController,
    viewModel: MovieListsViewModel,
    watchlistViewModel: WatchlistViewModel
) {

    val movieLists by viewModel.movieLists.collectAsStateWithLifecycle(initialValue = emptyList())
    val watchlistItems by watchlistViewModel.watchlist.collectAsStateWithLifecycle()
    val movies = watchlistItems.filter { it.mediaType == "movie" }

    LargeTopBarScaffold(
        title = "Movie lists",
        navigationIcon = { NavigateUpBtn(navController) },
        fab = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = { navController.navigate(NavRoutes.MOVIE_LISTS_CREATE_SCREEN) },
                text = { Text("Create list", style = MaterialTheme.typography.titleMedium) },
                icon = {
                    Symbol(
                        R.drawable.add_24px,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            MovieListsContent(movieLists, onLongPress = { viewModel.delete(it) }, movies = movies)
        }

    }


}