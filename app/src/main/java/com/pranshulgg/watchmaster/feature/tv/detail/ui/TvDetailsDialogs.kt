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
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@Composable
fun TvDetailsNoteDialog(
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    season: SeasonEntity?,
) {
    season?.let { seasonIt ->
        DialogBasic(
            show = viewModel.uiState.value.showNoteDialog,
            title = "Add a note",
            showDefaultActions = true,
            onDismiss = {
                viewModel.hideNoteDialog()
                viewModel.updateNoteText(seasonIt.seasonNotes ?: "")
            },
            onConfirm = {
                watchlistViewModel.setSeasonNote(seasonIt.showId, viewModel.uiState.value.note)
            },
            confirmText = "Save",
            content = {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Radius.Large),
                    value = viewModel.uiState.value.note,
                    onValueChange = { viewModel.updateNoteText(it) },
                    placeholder = { Text("Note...") }
                )
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
    season?.let { seasonIt ->
        DialogBasic(
            show = viewModel.uiState.value.showRatingDialog,
            title = "Rate this season",
            showDefaultActions = false,
            onDismiss = {
                viewModel.hideRatingDialog()
            },
            content = {
                RateMediaDialogContent(
                    onCancel = {
                        viewModel.hideRatingDialog()
                    },
                    onConfirm = { rating ->
                        watchlistViewModel.setSeasonUserRating(seasonIt.showId, rating)
                        watchlistViewModel.finishSeason(seasonIt.showId)
                    }
                )
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
    season?.let { seasonIt ->
        TextAlertDialog(
            show = viewModel.uiState.value.showConfirmationDialog,
            title = "Delete season",
            message = "Are you sure you want to delete this season? this action cannot be undone",
            confirmText = "Confirm",
            onConfirm = {
                if (seasonSize == 1) {
                    watchlistViewModel.delete(seasonIt.showId)
                } else {
                    watchlistViewModel.deleteSeason(seasonIt.showId)
                }
                SnackbarManager.show("Season deleted ${seasonIt.name}")
                navController.popBackStack()
            },
            onDismiss = {
                viewModel.hideConfirmationDialog()
            }
        )
    }
}
