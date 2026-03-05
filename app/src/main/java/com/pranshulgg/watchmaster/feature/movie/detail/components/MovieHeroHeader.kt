package com.pranshulgg.watchmaster.feature.movie.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.media.MediaDetailsScreenHeader
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieHeroHeader(
    movie: MovieBundle,
    watchlistItem: WatchlistItemEntity?,
    navController: NavController,
    isFinished: Boolean,
    userRating: Double? = 0.0,
    onUpdateRating: (Double) -> Unit
) {


    Box(modifier = Modifier.fillMaxWidth()) {

        AsyncImage(
            model = backdropUrl(movie),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            contentScale = ContentScale.Crop
        )

        MediaDetailsScreenHeader(
            navController,
            isFinished,
            userRating,
            onUpdateRating = onUpdateRating
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.3f to MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
                            1.0f to MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 1f)
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            Box(
                modifier = Modifier
                    .height(180.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(Radius.Large))
            ) {
                AsyncImage(
                    model = posterUrl(movie),
                    contentDescription = movie.title,
                    modifier = Modifier.matchParentSize()

                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        shadow = Shadow(
//                            color = Color.Black.copy(alpha = 0.4f),
                            color = MaterialTheme.colorScheme.surfaceContainer,
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
                Text(
                    text = "${formatRuntime(movie.runtime)} • ${watchlistItem?.releaseDate?.take(4) ?: "—"}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    movie.genres.forEach { genre ->
                        GenreChip(genre.name)
                    }
                    GenreChip("%.1f".format(watchlistItem?.avgRating), rating = true)

                }

            }
        }
    }
}


private fun formatRuntime(minutes: Int?): String {
    if (minutes == null || minutes <= 0) return "—"

    val h = minutes / 60
    val m = minutes % 60

    return if (h > 0) "${h}h ${m}m" else "${m}m"
}

@Composable
private fun GenreChip(text: String, rating: Boolean = false) {
    val schemeColor = MaterialTheme.colorScheme

    Surface(
        color = if (rating) schemeColor.tertiary else schemeColor.tertiaryContainer,
        shape = if (rating) RoundedCornerShape(Radius.Small) else CircleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = if (rating) 6.dp else 8.dp, end = 8.dp)
        ) {
            if (rating) {
                Symbol(R.drawable.star_24px, size = 16.dp, color = schemeColor.onTertiary)
                Spacer(Modifier.width(3.dp))
            }
            Text(
                text,
                color = if (rating) schemeColor.onTertiary else schemeColor.onTertiaryContainer,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun posterUrl(movie: MovieBundle): String? =
    when {
        movie.poster_path != null ->
            "https://image.tmdb.org/t/p/w500${movie.poster_path}"

        movie.images.posters.isNotEmpty() ->
            "https://image.tmdb.org/t/p/w500${movie.images.posters.first().file_path}"

        else -> null
    }

private fun backdropUrl(movie: MovieBundle): String? =
    when {
        movie.backdrop_path != null ->
            "https://image.tmdb.org/t/p/original${movie.backdrop_path}"

        movie.images.backdrops.isNotEmpty() ->
            "https://image.tmdb.org/t/p/original${movie.images.backdrops.first().file_path}"

        else -> null
    }
