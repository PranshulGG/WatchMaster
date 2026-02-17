package com.pranshulgg.watchmaster.screens.movieTabs.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.ui.components.DialogBasic
import com.pranshulgg.watchmaster.ui.components.RateMovieDialogContent
import com.pranshulgg.watchmaster.ui.components.SettingSection
import com.pranshulgg.watchmaster.ui.components.SettingTile
import com.pranshulgg.watchmaster.ui.components.SettingsTileIcon
import com.pranshulgg.watchmaster.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.ui.snackbar.SnackbarManager

@Composable
fun WatchlistItemOptionsSheetContent(
    selectedMovieItem: WatchlistItemEntity?,
    hideSheet: () -> Unit,
    onMovieDelete: (Long?) -> Unit,
    onMovieFinish: (Long?) -> Unit,
    onUpdateRating: (Float, Long) -> Unit,
) {

    val watchlistViewModel: WatchlistViewModel = viewModel()


    val markAsActionLabel = when (selectedMovieItem?.status) {
        WatchStatus.WATCHING -> "Mark as finished"
        WatchStatus.FINISHED -> "Reset to watchlist"
        WatchStatus.INTERRUPTED -> "Continue watching"
        else -> "Mark as watching"
    }

    val isMoviePinned = selectedMovieItem?.isPinned != true

    SettingSection(
        isModalOption = true,
        title = selectedMovieItem?.title,
        tiles = listOf(
            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.play_arrow_24px) },
                title = markAsActionLabel,
                onClick = {
                    hideSheet()

                    if (selectedMovieItem != null) {
                        when (selectedMovieItem.status) {
                            WatchStatus.WANT_TO_WATCH -> {
                                watchlistViewModel.start(selectedMovieItem.id)
                            }

                            WatchStatus.WATCHING -> {
                                onMovieFinish(selectedMovieItem.id)
                            }

                            WatchStatus.FINISHED -> {
                                watchlistViewModel.reset(selectedMovieItem.id)
                            }

                            else -> {
                                watchlistViewModel.start(selectedMovieItem.id)
                            }
                        }
                    }
                }
            ),
            if (selectedMovieItem?.status == WatchStatus.WATCHING) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.pause_24px) },
                    title = "Mark as interrupted",
                    onClick = {
                        hideSheet()
                        watchlistViewModel.interrupt(selectedMovieItem.id)
                    }
                )
            } else null,
            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.delete_24px) },
                title = "Delete",
                onClick = {
                    onMovieDelete(selectedMovieItem?.id)
                    hideSheet()

                }
            ),
            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.keep_24px) },
                title = if (!isMoviePinned) "Unpin" else "Pin",
                onClick = {
                    if (selectedMovieItem != null) {
                        watchlistViewModel.setPinned(selectedMovieItem.id, isMoviePinned)
                        SnackbarManager.show(if (isMoviePinned) "Movie pinned" else "Movie unpinned")
                    }
                    hideSheet()
                }
            ),

            if (selectedMovieItem?.status == WatchStatus.FINISHED) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.star_24px) },
                    title = "Update Rating",
                    onClick = {
                        onUpdateRating(
                            selectedMovieItem.userRating?.toFloat() ?: 0f,
                            selectedMovieItem.id
                        )
                        hideSheet()
                    }
                )
            } else null
        )
    )


}