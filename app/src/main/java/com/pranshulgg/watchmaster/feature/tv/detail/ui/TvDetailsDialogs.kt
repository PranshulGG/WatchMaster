package com.pranshulgg.watchmaster.feature.tv.detail.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.components.media.RateMediaDialogContent
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.DetailsConfirmationDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.DetailsNoteDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.DetailsRatingDialogContent
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@Composable
fun TvDetailsNoteDialog(
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    season: SeasonEntity?,
) {
    val uiState = viewModel.uiState.value

    season?.let { seasonIt ->
        DetailsNoteDialogContent(
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
        DetailsRatingDialogContent(
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
        DetailsConfirmationDialogContent(
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
