package com.pranshulgg.watchmaster.feature.tv.detail.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity

@Composable
fun EpisodeInfoHeader(episode: TvEpisodeEntity) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        PosterBox(
            posterUrl = "https://image.tmdb.org/t/p/original${episode.still_path}",
            apiPath = episode.still_path,
            width = 135.dp,
            height = 90.dp
        )

        Spacer(Modifier.width(16.dp))

        Column() {
            Text(
                text = episode.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "${formatRuntime(episode.runtime)} • ${episode.air_date ?: "—"}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatRuntime(minutes: Int?): String {
    if (minutes == null || minutes <= 0) return "—"

    val h = minutes / 60
    val m = minutes % 60

    return if (h > 0) "${h}h ${m}m" else "${m}m"
}
