package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EpisodeItem(item: TvEpisodeEntity, viewModel: TvDetailsViewModel, seasonStatus: WatchStatus) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent,
        onClick = {
            if (seasonStatus == WatchStatus.WANT_TO_WATCH) {
                SnackbarManager.show("Please mark the season as 'Watching' to track episodes")
                return@Surface
            }
            if (item.isWatched) {
                viewModel.markEpUnWatched(item.epId)
            } else {
                viewModel.markEpWatched(item.epId)
            }
        }
    ) {
        Row(
            modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp, start = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LeadingIcon(item.isWatched)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(3.dp))
                item.air_date?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


            IconButton(
                onClick = {},
                modifier = Modifier.size(36.dp),
                shapes = IconButtonDefaults.shapes()
            ) {
                Symbol(R.drawable.more_vert_24px)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LeadingIcon(isWatched: Boolean = false) {

    val theme = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.size(36.dp),
        color = if (isWatched) theme.primaryContainer else theme.surface,
        shape = if (isWatched) MaterialShapes.Cookie9Sided.toShape() else RoundedCornerShape(Radius.Full)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isWatched) {
                Symbol(R.drawable.check_24px, color = theme.onPrimaryContainer)
            } else {
                Symbol(R.drawable.schedule_24px, color = theme.onSurface)
            }
        }
    }
}
