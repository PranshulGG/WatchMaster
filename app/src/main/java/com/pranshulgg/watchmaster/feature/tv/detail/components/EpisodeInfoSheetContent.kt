package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity

@Composable
fun EpisodeInfoSheetContent(
    episode: TvEpisodeEntity,
) {
    EpisodeInfoHeader(episode)

    Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)) {

        episode.overview?.let {
            Text(
                text = "Overview",
                modifier = Modifier.padding(bottom = 2.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W700
            )
            Text(
                it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}


