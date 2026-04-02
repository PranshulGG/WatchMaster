package com.pranshulgg.watchmaster.feature.movie.lists.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.feature.shared.media.components.MovieWatchlistRow

@Composable
fun ViewMovieListContent(
    movies: List<WatchlistItemEntity>,
    navController: NavController,
    description: String
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        item {
            if (description.isNotBlank()) {
                MediaSectionCard(
                    title = "Description",
                    titleIcon = R.drawable.description_24px,
                    noPadding = true
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = description,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                
                Gap(12.dp)
            }
        }
        itemsIndexed(movies, key = ({ _, movie -> movie.id })) { index, mov ->
            MovieWatchlistRow(
                mov,
                index,
                movies,
                navController,
                onLongActionMovieRequest = {}
            )
            Gap(2.dp)
        }
    }
}