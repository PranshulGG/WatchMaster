package com.pranshulgg.watchmaster.feature.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.feature.search.SearchItem
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.data.getMovieGenreNames
import com.pranshulgg.watchmaster.feature.search.ui.SearchItemInfoSeasonSection
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchItemInfoSheetContent(
    item: SearchItem,
    seasonLoading: Boolean = true,
    seasonData: List<TvSeasonDto>,
    onSelectedSeason: (List<TvSeasonDto>?) -> Unit,
    watchlistViewModel: WatchlistViewModel
) {

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val genreList = item.genreIds?.let { getMovieGenreNames(it) }



    Column(
        Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PosterBox(
                posterUrl = poster,
                apiPath = item.posterPath,
                width = 80.dp,
                height = 120.dp,
                cornerRadius = ShapeRadius.Large,
                placeholder = { PosterPlaceholder(size = 0.6f) }
            )
            Spacer(Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 6.dp,
                )
            ) {
                Text(
                    item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 19.sp,
                )

                Row {

                    MediaChip(
                        if (item.releaseDate == "" || item.releaseDate == null) {
                            "No date"
                        } else {
                            item.releaseDate.take(
                                4
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(Modifier.width(3.dp))
                    MediaChip(
                        "%.1f".format(item.avg_rating),
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        shapeRadius = ShapeRadius.Small,
                        icon = R.drawable.star_24px
                    )
                }

            }
        }
        Spacer(Modifier.height(12.dp))

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            genreList?.forEach {
                MediaChip(
                    it,
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }

        }
        if (!seasonLoading && item.mediaType == "tv") {
            Spacer(Modifier.height(12.dp))
            SearchItemInfoSeasonSection(seasonData, onSelectedSeason, watchlistViewModel, item.id)
        } else if (seasonLoading && item.mediaType == "tv") {
            CircularProgressIndicator()
        }
        Spacer(Modifier.height(12.dp))

        item.overview?.let {
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 15,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(12.dp))

        }
    }

}