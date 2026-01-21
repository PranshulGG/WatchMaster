package com.pranshulgg.watchmaster.screens.movieTabs.finished

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity


@Composable
fun FinishedMovies(items: List<WatchlistItemEntity>) {
    Text("Completed movies ⭐", modifier = Modifier.padding(16.dp))
}
