package com.pranshulgg.watchmaster.screens.movieTabs.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.ui.components.SettingSection
import com.pranshulgg.watchmaster.ui.components.SettingTile
import com.pranshulgg.watchmaster.ui.components.SettingsTileIcon

@Composable
fun WatchlistItemOptionsSheetContent(
    selectedMovieItem: WatchlistItemEntity,
    hideSheet: () -> Unit
) {

    val watchlistViewModel: WatchlistViewModel = viewModel()


    val markAsActionLabel = when (selectedMovieItem.status) {
        WatchStatus.WATCHING -> "Mark as finished"
        WatchStatus.FINISHED -> "Reset to watchlist"
        WatchStatus.INTERRUPTED -> "Continue watching"
        else -> "Mark as watching"
    }

    SettingSection(
        isModalOption = true,
        title = selectedMovieItem.title,
        tiles = listOf(
            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.play_arrow_24px) },
                title = markAsActionLabel,
                onClick = {
                    when (selectedMovieItem.status) {
                        WatchStatus.WANT_TO_WATCH -> {
                            watchlistViewModel.start(selectedMovieItem.id)
                        }

                        WatchStatus.WATCHING -> {
                            watchlistViewModel.finish(selectedMovieItem.id)
                        }

                        WatchStatus.FINISHED -> {
                            watchlistViewModel.reset(selectedMovieItem.id)
                        }

                        else -> {
                            watchlistViewModel.start(selectedMovieItem.id)
                        }
                    }
                    hideSheet()
                }
            ),
            if (selectedMovieItem.status == WatchStatus.WATCHING) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.pause_24px) },
                    title = "Mark as interrupted",
                    onClick = {
                        watchlistViewModel.interrupt(selectedMovieItem.id)
                        hideSheet()
                    }
                )
            } else null,
            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.delete_24px) },
                title = "Delete",
                onClick = {
                    watchlistViewModel.delete(selectedMovieItem.id)
                    hideSheet()

                }
            ),
            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.keep_24px) },
                title = "Pin",
                onClick = {
                }
            ),

            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.star_24px) },
                title = "Rate",
                onClick = {
                }
            )
        )
    )

}