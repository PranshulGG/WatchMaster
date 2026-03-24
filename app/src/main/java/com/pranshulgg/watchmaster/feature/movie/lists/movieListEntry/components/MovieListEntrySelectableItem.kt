package com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.listItemShape
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.shared.media.components.WatchListStatusPill
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.asStatusDates
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.toWatchListItemStatusUiPill

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieListEntrySelectableItem(
    item: WatchlistItemEntity,
    index: Int,
    items: List<WatchlistItemEntity>,
    onMovieSelect: (WatchlistItemEntity) -> Unit,
    selectedMovies: List<WatchlistItemEntity>
) {

    val isOnly = items.singleOrNull() == item
    val isFirst = index == 0
    val isLast = index == items.lastIndex
    var selected by rememberSaveable(item.id) { mutableStateOf(false) }


    selected = selectedMovies.contains(item)

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val status = item.status.toWatchListItemStatusUiPill(item.asStatusDates())

    val shape = listItemShape(isOnly, isFirst, isLast)


    Surface(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clipToBounds()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Box(
                modifier = Modifier
                    .size(60.dp, height = 90.dp),
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

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 17.sp,
                )

                Spacer(Modifier.height(5.dp))
                WatchListStatusPill(
                    status.containerColor,
                    status.contentColor,
                    status.statusLabel,
                    item.status,
                    showRating = false
                )
            }

            Spacer(Modifier.weight(1f))
            FilledIconToggleButton(
                modifier = Modifier.size(44.dp),
                checked = selected,
                shapes = IconButtonDefaults.toggleableShapes(),
                colors = IconButtonDefaults.iconToggleButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                    checkedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                onCheckedChange = {
                    selected = it
                    onMovieSelect(item)
                }
            ) {
                if (!selected) {
                    Symbol(R.drawable.add_24px)
                } else {
                    Symbol(
                        R.drawable.close_24px,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }

    }

}

