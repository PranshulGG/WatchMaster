package com.pranshulgg.watchmaster.screens.media_detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.data.CastMember
import com.pranshulgg.watchmaster.ui.components.PosterBox


@Composable
fun CastItem(character: String, name: String, profilePath: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
    ) {
        PosterBox(
            posterUrl = "https://image.tmdb.org/t/p/w200${profilePath}",
            width = 80.dp,
            height = 80.dp,
            circular = true
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface

        )
        Text(
            text = character,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}