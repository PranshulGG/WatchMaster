package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EpisodeItem(
    item: TvEpisodeEntity,
    onTrailingAction: () -> Unit,
    carouselItemWidth: Dp,
    onItemClick: () -> Unit,
    isActive: Boolean = false,
) {

    Box(
        modifier = Modifier
            .width(carouselItemWidth)
            .height(130.dp)
            .clickable(
                onClick = {
                    onItemClick()
                }
            )
    ) {

        PosterBox(
            posterUrl = "https://image.tmdb.org/t/p/original${item.still_path}",
            apiPath = item.still_path,
            width = carouselItemWidth,
            height = 130.dp,
            cornerRadius = ShapeRadius.ExtraLarge,
            placeholder = { PosterPlaceholder(iconSize = 38.dp) },
            progressIndicatorSize = 34.dp
        )


        AnimatedVisibility(
            visible = isActive,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.matchParentSize()
        ) {
            Box(modifier = Modifier.matchParentSize()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                )

                Text(
                    "${item.episode_number} • ${item.name}",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(end = 12.dp, start = 12.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                )

                Box(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                ) {
                    LeadingIcon(item.isWatched)
                }


                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp),
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
            ShapeRadius.Full
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
