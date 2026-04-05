package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.theme.LocalStatusColors
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.core.ui.components.Symbol

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaStatusSection(status: WatchStatus, onClick: () -> Unit = {}) {
    val colorScheme = MaterialTheme.colorScheme
    val statusColor = LocalStatusColors.current

    val statusContainerColor = when (status) {
        WatchStatus.INTERRUPTED -> colorScheme.errorContainer
        WatchStatus.WATCHING -> statusColor.warning.bg
        WatchStatus.FINISHED -> statusColor.success.bg
        else -> statusColor.pending.bg
    }

    val statusContentColor = when (status) {
        WatchStatus.INTERRUPTED -> colorScheme.onErrorContainer
        WatchStatus.WATCHING -> statusColor.warning.on
        WatchStatus.FINISHED -> statusColor.success.on
        else -> statusColor.pending.on
    }

    val statusLabel = when (status) {
        WatchStatus.INTERRUPTED -> "Interrupted"
        WatchStatus.WATCHING -> "Progress"
        WatchStatus.FINISHED -> "Finished"
        else -> "Pending"
    }

    val statusIcon = when (status) {
        WatchStatus.INTERRUPTED -> R.drawable.play_arrow_24px
        WatchStatus.WATCHING -> R.drawable.pause_24px
        WatchStatus.FINISHED -> R.drawable.check_24px
        else -> R.drawable.schedule_24px
    }

    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Surface(
            color = statusContainerColor,
            shape = RoundedCornerShape(ShapeRadius.Large),
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Symbol(
                    statusIcon,
                    color = statusContentColor
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    statusLabel,
                    color = statusContentColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(Modifier.width(8.dp))

        OutlinedButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            onClick = {
                onClick()
            },
            contentPadding = ButtonDefaults.contentPaddingFor(48.dp),
            shapes = ButtonDefaults.shapes()
        ) {
            Text(
                "Where to watch",
                style = MaterialTheme.typography.titleMedium

            )
        }
    }
}