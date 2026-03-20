package com.pranshulgg.watchmaster.feature.movie.lists.create.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.ListItemShape
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.shared.media.components.WatchListStatusPill
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.asStatusDates
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.toWatchListItemStatusUiPill

@Composable
fun MovieListsSelectableItem(
    item: WatchlistItemEntity,
    index: Int,
    items: List<WatchlistItemEntity>,
    onMovieSelect: (Long) -> Unit
) {

    val isOnly = items.singleOrNull() == item
    val isFirst = index == 0
    val isLast = index == items.lastIndex
    var selected by rememberSaveable(item.id) { mutableStateOf(false) }

    val titleMaxLines = 2
    val overviewMaxLines = remember { mutableIntStateOf(1) }

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val status = item.status.toWatchListItemStatusUiPill(item.asStatusDates())

    val shape = ListItemShape(isOnly, isFirst, isLast)


    Surface(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(
                onClick = {
                    selected = !selected
                    onMovieSelect(item.id)
                }
            ),
        color = MaterialTheme.colorScheme.surfaceBright,
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
            AnimatedVisibility(
                visible = selected,
                enter = fadeIn(),
                exit = fadeOut()
            ) {


                Box(
                    modifier = Modifier
                        .size(80.dp, height = 120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (!poster.isNullOrBlank()) {
                        val painter = rememberAsyncImagePainter(model = poster)

                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop
                        )

                        if (painter.state is AsyncImagePainter.State.Loading) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        PosterPlaceholder()
                    }
                }
                SelectedBox()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
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
                Spacer(Modifier.height(5.dp))
                WatchListStatusPill(
                    status.containerColor,
                    status.contentColor,
                    status.statusLabel,
                    item.status,
                    item.userRating,
                )
            }
        }
    }
}

@Composable
private fun SelectedBox() {
    Box(
        modifier = Modifier
            .size(80.dp, height = 120.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Symbol(R.drawable.check_24px, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}