package com.pranshulgg.watchmaster.feature.tv.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.theme.LocalStatusColors
import com.pranshulgg.watchmaster.core.utils.formatDate
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity


data class WatchListTvStatusUi(
    val statusLabel: String,
    val containerColor: Color,
    val contentColor: Color
)

@Composable
fun WatchStatus.toWatchListTvStatusUi(item: SeasonEntity): WatchListTvStatusUi {
    val statusColor = LocalStatusColors.current

    return when (this) {
        WatchStatus.WATCHING -> {
            WatchListTvStatusUi(
                statusLabel = "Started • ${item.seasonStartedDate?.formatDate()}",
                containerColor = statusColor.warning.bg,
                contentColor = statusColor.warning.on
            )
        }

        WatchStatus.FINISHED -> {
            WatchListTvStatusUi(
                statusLabel = "Finished • ${item.seasonFinishedDate?.formatDate()}",
                containerColor = statusColor.success.bg,
                contentColor = statusColor.success.on
            )
        }

        WatchStatus.INTERRUPTED -> {
            WatchListTvStatusUi(
                statusLabel = "Interrupted • ${item.seasonInterruptedAt?.formatDate()}",
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        else -> {
            WatchListTvStatusUi(
                statusLabel = "Added • ${item.seasonAddedDate.formatDate()}",
                containerColor = statusColor.pending.bg,
                contentColor = statusColor.pending.on
            )
        }
    }


}