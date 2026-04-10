package com.pranshulgg.watchmaster.feature.lists.listEntry.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.listItemShape
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ListEntrySelectableMediaItem(
    item: WatchlistItemEntity,
    index: Int,
    items: List<WatchlistItemEntity>,
    onMovieSelect: (WatchlistItemEntity) -> Unit,
    selectedMovies: List<WatchlistItemEntity>
) {

    val isOnly = items.singleOrNull() == item
    val isFirst = index == 0
    val isLast = index == items.lastIndex

    val titleMaxLines = 2
    val overviewMaxLines = rememberSaveable { mutableIntStateOf(1) }
    var isReady by remember { mutableStateOf(false) }

    val selected = selectedMovies.contains(item)

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val shape = listItemShape(
        isOnly,
        isFirst,
        isLast
    )

    Surface(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .alpha(if (isReady) 1f else 0f)
            .combinedClickable(
                onClick = {
                    onMovieSelect(item)
                }
            ),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .clipToBounds()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp, height = 120.dp),
                contentAlignment = Alignment.Center
            ) {
                PosterBox(
                    posterUrl = poster,
                    apiPath = item.posterPath,
                    width = 80.dp,
                    height = 120.dp,
                    progressIndicatorSize = 40.dp,
                    cornerRadius = ShapeRadius.None
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                SelectedStatusPill(selected)
                Gap(5.dp)
                Text(
                    text = item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp,
                    maxLines = titleMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { result ->
                        val newLines = if (result.lineCount == 1) 2 else 1
                        if (overviewMaxLines.intValue != newLines) {
                            overviewMaxLines.intValue = newLines
                            isReady = true
                        } else {
                            isReady = true
                        }
                    }
                )
                Text(
                    item.overview ?: "No overview found",
                    maxLines = overviewMaxLines.intValue,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}


@Composable
private fun SelectedStatusPill(selected: Boolean = false) {
    MediaChip(
        text = if (selected) "Selected" else "Add",
        containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLowest,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        icon = if (selected) R.drawable.check_24px else R.drawable.add_24px,
        shapeRadius = if (selected) ShapeRadius.Small else ShapeRadius.Full
    )
}
