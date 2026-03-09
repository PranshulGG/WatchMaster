package com.pranshulgg.watchmaster.feature.tv.ui

import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.MovieHomeViewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaConfirmationDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaRatingDialogContent
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.confirmAction
import com.pranshulgg.watchmaster.feature.tv.TvHomeViewModel

@Composable
fun TvWatchlistConfirmationDialog(
    watchlistViewModel: WatchlistViewModel,
    tvHomeViewModel: TvHomeViewModel,
    season: SeasonEntity?,
) {
    val uiState = tvHomeViewModel.uiState.value

    season?.let { item ->
        MediaConfirmationDialogContent(
            uiState.showConfirmationDialog,
            tvHomeViewModel::hideConfirmationDialog,
            isTv = true,
            onConfirm = {
                watchlistViewModel.deleteSeason(item.seasonId)
                SnackbarManager.show("Season deleted ${item.name}")
            }
        )
    }
}

@Composable
fun TvWatchlistRatingDialog(
    watchlistViewModel: WatchlistViewModel,
    tvHomeViewModel: TvHomeViewModel,
    season: SeasonEntity?,
) {
    val uiState = tvHomeViewModel.uiState.value

    season?.let { item ->
        MediaRatingDialogContent(
            uiState.showRatingDialog,
            tvHomeViewModel::hideRatingDialog,
            isUpdateRating = uiState.isUpdateRating,
            originalRating = uiState.originalRating,
            onConfirm = { rating ->
                watchlistViewModel.setSeasonUserRating(item.seasonId, rating)
                watchlistViewModel.finishSeason(item.seasonId)
                if (uiState.isUpdateRating) SnackbarManager.show("User rating updated")
            }
        )
    }
}


@Composable
fun TvStatusWatchlistConfirmationDialog(
    watchlistViewModel: WatchlistViewModel,
    tvHomeViewModel: TvHomeViewModel,
    season: SeasonEntity?,
) {
    val uiState = tvHomeViewModel.uiState.value

    season?.let { item ->
        MediaConfirmationDialogContent(
            uiState.showStatusConfirmationDialog,
            tvHomeViewModel::hideStatusConfirmationDialog,
            status = item.status,
            isTv = true,
            onConfirm = {
                item.status.confirmAction(
                    start = {
                        watchlistViewModel.startSeason(item.seasonId)
                    },
                    finish = {
                    },
                    reset = {
                        watchlistViewModel.resetSeason(item.seasonId)
                    }
                )
            }
        )
    }
}