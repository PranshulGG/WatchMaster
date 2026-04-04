package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.core.ui.theme.ThemeVariantType
import com.pranshulgg.watchmaster.core.ui.theme.WatchMasterTheme
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EpisodeItem(
    item: TvEpisodeEntity,
    viewModel: TvDetailsViewModel,
    seasonStatus: WatchStatus,
    onTrailingAction: () -> Unit,
    prevEpWatched: Boolean = false,
) {

    Box(
        modifier = Modifier
            .size(182.dp, 120.dp)
            .clip(RoundedCornerShape(Radius.Medium))
            .clickable(
                onClick = {

                    if (!prevEpWatched) return@clickable

                    if (seasonStatus == WatchStatus.WANT_TO_WATCH || seasonStatus == WatchStatus.FINISHED) {
                        SnackbarManager.show("Please mark the season as 'Watching' to track episodes")
                        return@clickable
                    }

                    if (item.isWatched) {
                        viewModel.markEpUnWatched(item.epId)
                    } else {
                        viewModel.markEpWatched(item.epId)
                    }
                }
            )
    ) {

        PosterBox(
            posterUrl = "https://image.tmdb.org/t/p/original${item.still_path}",
            apiPath = item.still_path,
            width = 182.dp,
            height = 120.dp,
            placeholder = { PosterPlaceholder(iconSize = 38.dp) },
            progressIndicatorSize = 34.dp
        )


        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Color.Black.copy(alpha = 0.5f)
                )
        )

        Text(
            item.name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(end = 8.dp, start = 8.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
        )

        Box(
            Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) { LeadingIcon(item.isWatched) }


        Box(
            Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Tooltip(
                "Episode options",
                preferredPosition = TooltipAnchorPosition.Below,
                spacing = 5.dp
            ) {
                IconButton(
                    onClick = { onTrailingAction() },
                    modifier = Modifier
                        .size(36.dp),
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Symbol(R.drawable.more_vert_24px, color = Color.White)
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LeadingIcon(
    isWatched: Boolean = false,
) {

    val theme = MaterialTheme.colorScheme


    Surface(
        modifier = Modifier.size(36.dp),
        color = if (isWatched) theme.primaryContainer else Color.Gray,
        shape = if (isWatched) MaterialShapes.Cookie9Sided.toShape() else RoundedCornerShape(
            Radius.Full
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isWatched) {
                Symbol(R.drawable.check_24px, color = theme.onPrimaryContainer)
            } else {
                Symbol(R.drawable.schedule_24px, color = Color.White)
            }
        }
    }
}
