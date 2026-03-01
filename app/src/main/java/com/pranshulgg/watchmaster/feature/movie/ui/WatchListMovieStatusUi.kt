package com.pranshulgg.watchmaster.feature.movie.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.theme.LocalStatusColors
import com.pranshulgg.watchmaster.core.utils.formatDate
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity


data class WatchListMovieStatusUi(
    val statusLabel: String,
    val containerColor: Color,
    val contentColor: Color
)

@Composable
fun WatchStatus.toWatchListMovieStatusUi(item: WatchlistItemEntity): WatchListMovieStatusUi {
    val statusColor = LocalStatusColors.current

    return when (this) {
        WatchStatus.WATCHING -> {
            WatchListMovieStatusUi(
                statusLabel = "Started • ${item.startedDate?.formatDate()}",
                containerColor = statusColor.warning.bg,
                contentColor = statusColor.warning.on
            )
        }

        WatchStatus.FINISHED -> {
            WatchListMovieStatusUi(
                statusLabel = "Finished • ${item.finishedDate?.formatDate()}",
                containerColor = statusColor.success.bg,
                contentColor = statusColor.success.on
            )
        }

        WatchStatus.INTERRUPTED -> {
            WatchListMovieStatusUi(
                statusLabel = "Interrupted • ${item.interruptedAt?.formatDate()}",
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        else -> {
            WatchListMovieStatusUi(
                statusLabel = "Added • ${item.addedDate.formatDate()}",
                containerColor = statusColor.pending.bg,
                contentColor = statusColor.pending.on
            )
        }
    }


}