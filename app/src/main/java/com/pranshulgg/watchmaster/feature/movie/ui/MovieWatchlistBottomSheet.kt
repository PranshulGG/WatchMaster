package com.pranshulgg.watchmaster.feature.movie.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.MovieHomeViewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.WatchlistMediaSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieWatchlistBottomSheet(
    show: Boolean = false,
    sheetState: SheetState,
    watchlistItem: WatchlistItemEntity?,
    onDismiss: () -> Unit,
    viewModel: WatchlistViewModel,
    movieHomeViewModel: MovieHomeViewModel
) {

    if (show && watchlistItem != null)
        ActionBottomSheet(
            showActions = false,
            sheetState = sheetState,
            onCancel = { onDismiss() },
            onConfirm = { },
        ) {
            WatchlistMediaSheetContent(
                id = watchlistItem.id,
                mediaTitle = watchlistItem.title,
                status = watchlistItem.status,
                onUpdateRating = {
                    movieHomeViewModel.showUpdateRatingDialog()
                },
                onWatchStatus = { status ->
                    if (status != WatchStatus.WATCHING) {
                        movieHomeViewModel.showStatusConfirmationDialog()
                    } else {
                        movieHomeViewModel.showRatingDialog()
                    }
                },
                onDelete = {
                    movieHomeViewModel.showConfirmationDialog()
                },
                onDismiss = { onDismiss() },
                viewModel = viewModel,
                isPinned = watchlistItem.isPinned,
            )
        }
}