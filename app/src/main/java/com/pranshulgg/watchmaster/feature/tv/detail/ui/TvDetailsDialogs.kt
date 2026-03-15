package com.pranshulgg.watchmaster.feature.tv.detail.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaConfirmationDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaNoteDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaRatingDialogContent
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@Composable
fun TvDetailsNoteDialog(
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    season: SeasonEntity?,
) {
    val uiState = viewModel.uiState.value

    season?.let { seasonIt ->
        MediaNoteDialogContent(
            uiState.showNoteDialog,
            uiState.note,
            seasonIt.seasonNotes ?: "",
            viewModel::updateNoteText,
            viewModel::hideNoteDialog,
            onConfirm = {
                watchlistViewModel.setSeasonNote(seasonIt.seasonId, it)
            }
        )
    }
}

@Composable
fun TvDetailsRatingDialog(
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    season: SeasonEntity?,
) {
    val uiState = viewModel.uiState.value

    season?.let { seasonIt ->
        MediaRatingDialogContent(
            uiState.showRatingDialog,
            viewModel::hideRatingDialog,
            onConfirm = { rating ->
                watchlistViewModel.setSeasonUserRating(seasonIt.seasonId, rating)
                watchlistViewModel.finishSeason(seasonIt.seasonId)
            }
        )
    }
}


@Composable
fun TvDetailsConfirmationDialog(
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    season: SeasonEntity?,
    seasonSize: Int,
    navController: NavController
) {
    val uiState = viewModel.uiState.value

    season?.let { seasonIt ->
        MediaConfirmationDialogContent(
            uiState.showConfirmationDialog,
            viewModel::hideConfirmationDialog,
            onConfirm = {
                if (seasonSize == 1) {
                    watchlistViewModel.delete(seasonIt.showId)
                } else {
                    watchlistViewModel.deleteSeason(seasonIt.seasonId)
                }
                SnackbarManager.show("Season deleted ${seasonIt.name}")
                navController.popBackStack()
            },
            isTv = true
        )
    }
}


