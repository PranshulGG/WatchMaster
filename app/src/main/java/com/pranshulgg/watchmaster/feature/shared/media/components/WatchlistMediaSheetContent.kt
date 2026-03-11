package com.pranshulgg.watchmaster.feature.shared.media.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.SettingSection
import com.pranshulgg.watchmaster.core.ui.components.SettingTile
import com.pranshulgg.watchmaster.core.ui.components.SettingsTileIcon
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.actionLabel
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.confirmAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistMediaSheetContent(
    id: Long,
    isTv: Boolean = false,
    isPinned: Boolean = false,
    mediaTitle: String?,
    status: WatchStatus,
    onUpdateRating: () -> Unit,
    onWatchStatus: (WatchStatus) -> Unit,
    onDeleteSeries: (seriesId: Long) -> Unit = {},
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: WatchlistViewModel,
    mediaOptions: Boolean = true
) {
    SettingSection(
        isModalOption = true,
        title = mediaTitle,
        tiles = listOf(

            if (mediaOptions) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.play_arrow_24px) },
                    title = status.actionLabel,
                    onClick = {
                        onWatchStatus(status)
                        onDismiss()
                    }
                )
            } else null,

            if (status == WatchStatus.WATCHING && mediaOptions) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.pause_24px) },
                    title = "Mark as interrupted",
                    onClick = {
                        if (isTv) {
                            viewModel.interruptSeason(id)
                        } else {
                            viewModel.interrupt(id)
                        }
                        onDismiss()
                    }
                )
            } else null,

            SettingTile.ActionTile(
                leading = { SettingsTileIcon(R.drawable.delete_24px) },
                title = if ((isTv && !mediaOptions)) "Delete series" else "Delete",
                onClick = {
                    if ((isTv && !mediaOptions)) {
                        onDeleteSeries(id)
                    } else {
                        onDelete()
                    }
                    onDismiss()
                }
            ),

            if (!isTv || !mediaOptions) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.keep_24px) },
                    title = if (isPinned) "Unpin" else "Pin",
                    onClick = {
                        viewModel.setPinned(id, !isPinned)
                        onDismiss()
                    }
                )
            } else null,

            if (status == WatchStatus.FINISHED && mediaOptions) {
                SettingTile.ActionTile(
                    leading = { SettingsTileIcon(R.drawable.star_24px) },
                    title = "Update Rating",
                    onClick = {
                        onUpdateRating()
                        onDismiss()
                    }
                )
            } else null
        )
    )

}