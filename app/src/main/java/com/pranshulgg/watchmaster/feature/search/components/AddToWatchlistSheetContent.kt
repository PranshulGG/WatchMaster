package com.pranshulgg.watchmaster.feature.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.SeasonData
import com.pranshulgg.watchmaster.feature.search.SearchItem
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.SettingSection
import com.pranshulgg.watchmaster.core.ui.components.SettingTile
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.getMovieGenreNames
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddToWatchlistSheetContent(
    item: SearchItem,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    seasonLoading: Boolean = true,
    seasonData: List<TvSeasonDto>,
    onSelectedSeason: (List<TvSeasonDto>) -> Unit
) {
    val scrollState = rememberScrollState()

    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val genreList = item.genreIds?.let { getMovieGenreNames(it) }
    val size = ButtonDefaults.MediumContainerHeight


    val btnEnabled =
        item.mediaType == "movie" ||
                (item.mediaType == "tv" && !seasonLoading)


//    val btnSize = ButtonDefaults.MediumContainerHeight

    Column(
        Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
                    StarDateChip(
                        if (item.releaseDate == "" || item.releaseDate == null) {
                            "No date"
                        } else {
                            item.releaseDate.take(
                                4
                            )
                        }, isDate = true
                    )
                    Spacer(Modifier.width(3.dp))
                    StarDateChip("%.1f".format(item.avg_rating))
                }

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
        if (!seasonLoading && item.mediaType == "tv") {
            Spacer(Modifier.height(12.dp))
            SeasonBtn(seasonData, onSelectedSeason, item.id)
        } else if (seasonLoading && item.mediaType == "tv") {
            CircularProgressIndicator()
        }
        Spacer(Modifier.height(12.dp))
//
//        Box(
//            Modifier
//                .weight(1f, fill = false)
//                .verticalColumnScrollbar(
//                    scrollState = scrollState,
//                    alpha = scrollbarAlpha
//                )
//                .verticalScroll(scrollState)
//        ) {
//
//        }


        item.overview?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 15,
                overflow = TextOverflow.Ellipsis
            )
        }


        Spacer(Modifier.height(16.dp))

//        seasonData?.forEach {
//            Text(it.name)
//        }

        val interactionSources = remember { List(2) { MutableInteractionSource() } }

        ButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            overflowIndicator = { }
        ) {
            customItem(
                {
                    Button(
                        interactionSource = interactionSources[0],
                        modifier = Modifier
                            .weight(0.7f)
                            .heightIn(size)
                            .animateWidth(interactionSources[0]),
                        onClick = { onCancel() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shapes = ButtonDefaults.shapes(),
                        contentPadding = ButtonDefaults.contentPaddingFor(size),
                    ) {
                        Text(
                            "Cancel",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = ButtonDefaults.textStyleFor(size)
                        )
                    }
                },
                { state ->

                }
            )
            customItem(
                {
                    Button(
//                        enabled = item.mediaType == "tv" && !seasonLoading,
                        enabled = btnEnabled,
                        interactionSource = interactionSources[1],
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(size)
                            .animateWidth(interactionSources[1]),
                        onClick = {
                            onConfirm()
                        },
                        shapes = ButtonDefaults.shapes(),
                        contentPadding = ButtonDefaults.contentPaddingFor(size),
                    ) {
                        Text("Add to watchlist", style = ButtonDefaults.textStyleFor(size))
                    }

                },
                { state ->

                }
            )

        }
    }

}

@Composable
private fun GenreChip(text: String) {
    val schemeColor = MaterialTheme.colorScheme

    Surface(
        color = schemeColor.tertiaryContainer,
        shape = CircleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        ) {
            Text(
                text,
                color = schemeColor.onTertiaryContainer,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StarDateChip(text: String, isDate: Boolean = false) {
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


@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SeasonBtn(
    seasonData: List<TvSeasonDto>,
    onSelectedSeason: (List<TvSeasonDto>) -> Unit,
    id: Long,
) {
    var selectedSeason by remember { mutableIntStateOf(0) }
    val size = ButtonDefaults.MediumContainerHeight
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var seasonChanged by remember { mutableStateOf(false) }

    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    val seasonsData by watchlistViewModel
        .seasonsForShow(id)
        .collectAsState(initial = emptyList())

//
//    LaunchedEffect(id) {
//        watchlistViewModel.observeItem(id)
//    }


    if (!seasonChanged) {
        onSelectedSeason(listOf(seasonData[0]))
    }

    val totalSeasons = remember { mutableStateListOf<TvSeasonDto>() }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        FilledTonalButton(
            modifier = Modifier
                .heightIn(size)
                .fillMaxWidth(),
            shapes = ButtonDefaults.shapes(),
            onClick = {
                scope.launch {
                    sheetState.show()
                }
            },
            contentPadding = ButtonDefaults.contentPaddingFor(size)
        ) {
            Symbol(
                R.drawable.list_alt_24px,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                size = ButtonDefaults.iconSizeFor(size)
            )
            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
            Text(seasonData[selectedSeason].name, style = ButtonDefaults.textStyleFor(size))
        }
    }


    ActionBottomSheet(
        sheetState = sheetState,
        onCancel = {
            scope.launch { sheetState.hide() }
        },
        onConfirm = {
            scope.launch { sheetState.hide() }
        },
        showActions = false,
    ) {

        SettingSection(
            title = "All seasons",
            isModalOption = true,
            tiles = seasonData.mapIndexed { index, item ->


                val seasonExists = seasonsData.any { it.name == item.name }

                SettingTile.ActionTile(
                    title = item.name,
                    leading = {
                        if (index == selectedSeason) {
                            Symbol(
                                R.drawable.check_24px,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    },
                    description = item.air_date,
                    selected = index == selectedSeason,

                    onClick = {
                        if (!seasonExists) {
                            seasonChanged = true
                            selectedSeason = index
                            if (!totalSeasons.contains(item)) {
                                totalSeasons.add(item)
                            }
                            onSelectedSeason(totalSeasons)
                            scope.launch { sheetState.hide() }
                        }
                    },
                    trailing = {
                        Surface(
                            color = if (index == selectedSeason) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceBright,
                            shape = RoundedCornerShape(if (index == selectedSeason) Radius.Small else Radius.Full)
                        ) {
                            Text(
                                item.episode_count.toString() + " episodes ${if (seasonExists) "• Saved" else ""}",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                color = if (index == selectedSeason) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    colorDesc = if (index == selectedSeason) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )


    }
}
