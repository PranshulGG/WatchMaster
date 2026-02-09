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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextLayoutResult
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
        isOnly -> RoundedCornerShape(16.dp)
        isFirst -> RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 4.dp,
            bottomEnd = 4.dp
        )

        isLast -> RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )

        else -> RoundedCornerShape(4.dp)
    }

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val titleMaxLines = 2
    val overviewMaxLines = remember { mutableIntStateOf(1) }


    Surface(
        shape = shape,
        modifier = Modifier
            .clip(shape)
            .clickable { onAddToWatchlist() },
        color = MaterialTheme.colorScheme.surfaceBright

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .clipToBounds()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp, height = 120.dp),
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
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp,
                    maxLines = titleMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult: TextLayoutResult ->
                        overviewMaxLines.value = if (textLayoutResult.lineCount == 1) 2 else 1
                    }
                )
                Text(
                    item.overview ?: "No overview found",
                    maxLines = overviewMaxLines.value,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
                Spacer(Modifier.height(5.dp))
                Row {
                    StarDateChip(
                        if (item.releaseDate == "" || item.releaseDate == null) {
                            "No date"
                        } else {
                            item.releaseDate.take(
                                4
                            )
                        }, isDate = true
                    )
                    Spacer(Modifier.width(5.dp))
                    StarDateChip("%.1f".format(item.avg_rating))
                }
            }
        }
    }
}


fun isoToName(code: String): String {
    val locale = Locale.forLanguageTag(code)
    val name = locale.getDisplayLanguage(Locale.ENGLISH).uppercase()
    return name.ifBlank { code }
}

@Composable
private fun StarDateChip(text: String, isDate: Boolean = false) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
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


