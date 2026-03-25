package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.AvatarIcon
import com.pranshulgg.watchmaster.core.ui.components.AvatarMonogram
import com.pranshulgg.watchmaster.core.ui.components.TooltipIconBtn
import com.pranshulgg.watchmaster.core.ui.components.listItemShape
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.mapper.toIcon
import com.pranshulgg.watchmaster.feature.shared.media.components.MediaListsRow

@Composable
fun MovieListsContent(
    movieLists: List<MovieListsEntity>,
    onClick: (Long) -> Unit,
    movies: List<WatchlistItemEntity>
) {

    val colorScheme = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(
            movieLists,
            key = { _, item -> item.id }) { index, item ->

            val moviesFiltered = movies.filter { item.movieIds.contains(it.id) }

            val isOnly = movieLists.singleOrNull() == item
            val isFirst = index == 0
            val isLast = index == movieLists.lastIndex

            val shape = listItemShape(isOnly, isFirst, isLast)

            MediaListsRow(
                headline = item.name,
                description = item.description,
                shapes = shape,
                onClick = {
                    onClick(item.id)
                },
                leading = {
                    Box(modifier = Modifier.padding(top = 0.dp)) {
                        AvatarIcon(
                            item.icon?.toIcon() ?: R.drawable.folder_24px,
                            containerColor = colorScheme.tertiaryContainer,
                            contentColor = colorScheme.onTertiaryContainer,
                        )
                    }
                },

                media = {
                    if (!moviesFiltered.isEmpty()) {
                        Row(
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            moviesFiltered.take(4).forEachIndexed { moveIndex, mov ->
                                Box(
                                    modifier = Modifier.offset(x = (-12).dp * moveIndex)
                                ) {
                                    PosterBox(
                                        posterUrl = "https://image.tmdb.org/t/p/w154${mov.posterPath}",
                                        apiPath = mov.posterPath,
                                        width = 40.dp,
                                        height = 40.dp,
                                        circular = true
                                    )
                                }
                            }

                            if (moviesFiltered.size > 4) {
                                Box(
                                    modifier = Modifier.offset(x = (-12).dp * 4)
                                ) {
                                    AvatarMonogram(
                                        "${moviesFiltered.size}+",
                                        containerColor = colorScheme.surfaceContainer,
                                        contentColor = colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            "No movies found",
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    }
}