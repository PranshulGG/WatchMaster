package com.pranshulgg.watchmaster.screens.search.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.getGenreNames
import com.pranshulgg.watchmaster.screens.search.SearchItem
import com.pranshulgg.watchmaster.ui.components.PosterPlaceholder
import com.pranshulgg.watchmaster.ui.components.rememberScrollbarAlpha
import com.pranshulgg.watchmaster.ui.components.verticalColumnScrollbar
import com.pranshulgg.watchmaster.utils.Symbol
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddToWatchlistDialogContent(item: SearchItem, onCancel: () -> Unit, onConfirm: () -> Unit) {
    val scrollState = rememberScrollState()
    val scrollbarAlpha = rememberScrollbarAlpha(scrollState)


    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val genreList = item.genreIds?.let { getGenreNames(it) }
//    val btnSize = ButtonDefaults.MediumContainerHeight

    Column(
        Modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .clip(RoundedCornerShape(16.dp)),
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
                    PosterPlaceholder(bgColor = MaterialTheme.colorScheme.surface)
                }
            }
            Spacer(Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 6.dp
                )
            ) {
                Text(
                    item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 19.sp,
                )

                StarDateChip(
                    if (item.releaseDate == "" || item.releaseDate == null) {
                        "No date"
                    } else {

                        "${item.mediaType.replaceFirstChar { it.uppercase() }} • ${
                            item.releaseDate.take(
                                4
                            )
                        }"
                    }, isDate = true
                )
                StarDateChip("%.1f".format(item.avg_rating))

            }
        }

        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .weight(1f, fill = false)
                .verticalColumnScrollbar(
                    scrollState = scrollState,
                    alpha = scrollbarAlpha
                )
                .verticalScroll(scrollState)
        ) {
            item.overview?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            genreList?.forEach {
                GenreChip(it)
            }

        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.35f),
                onClick = { onCancel() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                shapes = ButtonDefaults.shapes()
            ) {
                Text("Cancel", color = MaterialTheme.colorScheme.onErrorContainer)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onConfirm() },
                shapes = ButtonDefaults.shapes()
            ) {
                Text("Add to watchlist")
            }

        }


    }

}


@Composable
fun StarDateChip(text: String, isDate: Boolean = false) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceBright,
        shape = CircleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = if (isDate) 8.dp else 5.dp, end = 8.dp)
        ) {
            if (!isDate) {
                Symbol(
                    R.drawable.star_24px,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    size = 16.dp,
                )
                Spacer(Modifier.width(3.dp))
            }
            Text(
                text,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun GenreChip(label: String) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier
            .height(32.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = shape
            )
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}

