package com.pranshulgg.watchmaster.feature.tv.detail.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity

@Composable
fun EpisodeInfoHeader(episode: TvEpisodeEntity) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${episode.still_path}",
            contentDescription = episode.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.3f to MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.5f),
                            1.0f to MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 1f)
                        )
                    )
                )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                5.dp,
                alignment = Alignment.Bottom
            ),
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        offset = Offset(2f, 2f),
                        blurRadius = 6f
                    )
                ),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                lineHeight = 30.sp,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.height(3.dp))
            MediaChip(
                text = formatRuntime(episode.runtime),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                icon = R.drawable.hourglass_top_24px
            )


            MediaChip(
                text = episode.air_date ?: "—",
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                icon = R.drawable.date_range_24px,
                shapeRadius = Radius.Small
            )

        }
    }
}

private fun formatRuntime(minutes: Int?): String {
    if (minutes == null || minutes <= 0) return "—"

    val h = minutes / 60
    val m = minutes % 60

    return if (h > 0) "${h}h-${m}m" else "${m}m"
}
