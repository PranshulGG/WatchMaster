package com.pranshulgg.watchmaster.screens.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.getGenreNames
import com.pranshulgg.watchmaster.screens.search.SearchItem
import com.pranshulgg.watchmaster.ui.components.PosterPlaceholder
import com.pranshulgg.watchmaster.utils.Symbol
import java.util.Locale

@Composable
fun SearchRow(
    item: SearchItem,
    index: Int,
    results: List<SearchItem>,
    onAddToWatchlist: () -> Unit,
) {

    val isOnly = results.singleOrNull() == item
    val isFirst = index == 0
    val isLast = index == results.lastIndex

    val shape = when {
        isOnly -> RoundedCornerShape(18.dp)
        isFirst -> RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
        isLast -> RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)
        else -> RoundedCornerShape(0.dp)
    }

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }



    Surface(
        shape = shape,
        modifier = Modifier
            .clip(shape)
            .clickable { onAddToWatchlist() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
            ),
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
                    PosterPlaceholder()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape,
                ) {
                    Text(
                        isoToName(item.originalLanguage ?: "en"),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Text(
                    text = item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 19.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    "${
                        if (item.releaseDate == "" || item.releaseDate == null) {
                            "No date"
                        } else {
                            item.releaseDate.take(4)
                        }
                    } • ${
                        if (!item.genreIds.isNullOrEmpty()) {
                            getGenreNames(item.genreIds).joinToString(", ")
                        } else {
                            "No genre found"
                        }

                    }",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = TextUnit(16f, TextUnitType.Sp),
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(5.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    shape = CircleShape
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 5.dp, end = 8.dp)
                    ) {
                        Symbol(
                            R.drawable.star_24px,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            size = 16.dp,
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            "%.1f".format(item.avg_rating),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


fun isoToName(code: String): String {
    val locale = Locale.forLanguageTag(code)
    val name = locale.getDisplayLanguage(Locale.ENGLISH)
    return name.ifBlank { code }
}
