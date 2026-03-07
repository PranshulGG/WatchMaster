package com.pranshulgg.watchmaster.feature.movie.detail.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailsViewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaConfirmationDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaNoteDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaRatingDialogContent

@Composable
fun MovieDetailsNoteDialog(
    viewModel: MovieDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    watchlistItem: WatchlistItemEntity?,
) {
    val uiState = viewModel.uiState.value

    watchlistItem?.let { item ->
        MediaNoteDialogContent(
            uiState.showNoteDialog,
            uiState.note,
            item.notes ?: "",
            viewModel::updateNoteText,
            viewModel::hideNoteDialog,
            onConfirm = {
                watchlistViewModel.setNote(item.id, uiState.note)
            }
        )
    }
}

@Composable
fun MovieDetailsRatingDialog(
    viewModel: MovieDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    watchlistItem: WatchlistItemEntity?,
) {
    val uiState = viewModel.uiState.value

    watchlistItem?.let { item ->
        MediaRatingDialogContent(
            uiState.showRatingDialog,
            viewModel::hideRatingDialog,
            onConfirm = { rating ->
                watchlistViewModel.setUserRating(item.id, rating)
                watchlistViewModel.finish(item.id)
            }
        )
    }
}


@Composable
fun MovieDetailsConfirmationDialog(
    viewModel: MovieDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    watchlistItem: WatchlistItemEntity?,
    navController: NavController
) {
    val uiState = viewModel.uiState.value

    watchlistItem?.let { item ->
        MediaConfirmationDialogContent(
            uiState.showConfirmationDialog,
            viewModel::hideConfirmationDialog,
            onConfirm = {
                watchlistViewModel.delete(item.id)
                SnackbarManager.show("Movie deleted ${item.title}")
                navController.popBackStack()
            }
        )
    }
}
