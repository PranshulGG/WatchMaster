package com.pranshulgg.watchmaster.feature.movie.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.MovieHomeViewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaConfirmationDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaRatingDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.confirmAction


@Composable
fun MovieWatchlistConfirmationDialog(
    watchlistViewModel: WatchlistViewModel,
    movieHomeViewModel: MovieHomeViewModel,
    watchlistItem: WatchlistItemEntity?,
) {
    val uiState = movieHomeViewModel.uiState.value

    watchlistItem?.let { item ->
        MediaConfirmationDialogContent(
            uiState.showConfirmationDialog,
            movieHomeViewModel::hideConfirmationDialog,
            onConfirm = {
                watchlistViewModel.delete(item.id)
                SnackbarManager.show("Movie deleted ${item.title}")
            }
        )
    }
}

@Composable
fun MovieWatchlistRatingDialog(
    watchlistViewModel: WatchlistViewModel,
    movieHomeViewModel: MovieHomeViewModel,
    watchlistItem: WatchlistItemEntity?,
) {
    val uiState = movieHomeViewModel.uiState.value

    watchlistItem?.let { item ->
        MediaRatingDialogContent(
            uiState.showRatingDialog,
            movieHomeViewModel::hideRatingDialog,
            isUpdateRating = uiState.isUpdateRating,
            originalRating = uiState.originalRating,
            onConfirm = { rating ->
                watchlistViewModel.setUserRating(item.id, rating)
                watchlistViewModel.finish(item.id)
                if (uiState.isUpdateRating) SnackbarManager.show("User rating updated")
            }
        )
    }
}


@Composable
fun MovieStatusWatchlistConfirmationDialog(
    watchlistViewModel: WatchlistViewModel,
    movieHomeViewModel: MovieHomeViewModel,
    watchlistItem: WatchlistItemEntity?,
) {
    val uiState = movieHomeViewModel.uiState.value

    watchlistItem?.let { item ->
        MediaConfirmationDialogContent(
            uiState.showStatusConfirmationDialog,
            movieHomeViewModel::hideStatusConfirmationDialog,
            status = item.status,
            onConfirm = {
                item.status.confirmAction(
                    start = {
                        watchlistViewModel.start(item.id)
                    },
                    finish = {
                    },
                    reset = {
                        watchlistViewModel.reset(item.id)
                    }
                )
            }
        )
    }
}