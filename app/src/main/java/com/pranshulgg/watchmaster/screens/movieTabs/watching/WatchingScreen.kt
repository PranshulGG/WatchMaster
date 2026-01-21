package com.pranshulgg.watchmaster.screens.movieTabs.watching

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity


@Composable
fun WatchingMovies(items: List<WatchlistItemEntity>) {
    Text("Currently watching 🎬", modifier = Modifier.padding(16.dp))
}
