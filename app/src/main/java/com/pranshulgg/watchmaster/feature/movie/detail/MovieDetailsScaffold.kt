package com.pranshulgg.watchmaster.feature.movie.detail

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.feature.movie.detail.ui.MovieDetailsConfirmationDialog
import com.pranshulgg.watchmaster.feature.movie.detail.ui.MovieDetailsNoteDialog
import com.pranshulgg.watchmaster.feature.movie.detail.ui.MovieDetailsRatingDialog
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.ui.FloatingToolbarMediaActionsParams
import com.pranshulgg.watchmaster.feature.shared.media.ui.MediaActionsFloatingToolbar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailsScaffold(
    id: Long,
    scrollBehavior: FloatingToolbarScrollBehavior,
    viewModel: MovieDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    navController: NavController
) {

    val watchlistFlow = remember(id) { watchlistViewModel.item(id) }
    val watchlistItem by watchlistFlow.collectAsStateWithLifecycle()

    val movieItem = viewModel.state

    val loading = viewModel.loading
    val isMoviePinned = watchlistItem?.isPinned == true

    if (loading || movieItem == null) {
        LoadingScreenPlaceholder()
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            MediaActionsFloatingToolbar(
                scrollBehavior,
                watchlistItem?.status ?: WatchStatus.WATCHING,
                actions = FloatingToolbarMediaActionsParams(
                    startWatching = { watchlistViewModel.start(id) },
                    resetWatching = { watchlistViewModel.reset(id) },
                    finishWatching = { viewModel.showRatingDialog() },
                    interruptWatching = { watchlistViewModel.interrupt(id) },
                    delete = { viewModel.showConfirmationDialog() },
                    togglePin = { watchlistViewModel.setPinned(id, !isMoviePinned) }
                ),
                isMoviePinned
            )
        },

        ) { _ ->

        MovieDetailsContent(
            movieItem,
            watchlistItem,
            navController,
            scrollBehavior,
            viewModel,
            watchlistViewModel,
        )
    }


    MovieDetailsNoteDialog(viewModel, watchlistViewModel, watchlistItem)
    MovieDetailsRatingDialog(viewModel, watchlistViewModel, watchlistItem)
    MovieDetailsConfirmationDialog(viewModel, watchlistViewModel, watchlistItem, navController)
}