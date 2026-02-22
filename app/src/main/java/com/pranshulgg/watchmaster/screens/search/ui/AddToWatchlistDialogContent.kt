package com.pranshulgg.watchmaster.screens.search.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuGroupShapes
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.MenuItemShapes
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.getGenreNames
import com.pranshulgg.watchmaster.screens.search.SearchItem
import com.pranshulgg.watchmaster.screens.search.SearchTvEntity
import com.pranshulgg.watchmaster.ui.components.PosterPlaceholder
import com.pranshulgg.watchmaster.ui.components.rememberScrollbarAlpha
import com.pranshulgg.watchmaster.ui.components.verticalColumnScrollbar
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol


private val mockResults = listOf(
    SearchTvEntity(
        season_number = 1,
        episode_count = 10,
        name = "Season 1",
        poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
        air_date = "2024-10-17",
    ),
    SearchTvEntity(
        season_number = 2,
        episode_count = 10,
        name = "Season 2",
        poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
        air_date = "2024-10-17",
    ),
    SearchTvEntity(
        season_number = 3,
        episode_count = 10,
        name = "Season 3",
        poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
        air_date = "2024-10-17",
    ),
    SearchTvEntity(
        season_number = 4,
        episode_count = 10,
        name = "Season 4",
        poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
        air_date = "2024-10-17",
    )
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddToWatchlistDialogContent(
    item: SearchItem,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    seasonLoading: Boolean = true,
    seasonData: List<SearchTvEntity> = emptyList()
) {
    val scrollState = rememberScrollState()
    val scrollbarAlpha = rememberScrollbarAlpha(scrollState)


    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val genreList = item.genreIds?.let { getGenreNames(it) }
    val size = ButtonDefaults.MediumContainerHeight

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

//        if (!seasonLoading) {
        SplitButtonWithDropdownMenuSample(mockResults)
//        }
        Spacer(Modifier.height(12.dp))
//
//        FlowRow(
//            verticalArrangement = Arrangement.spacedBy(6.dp),
//            horizontalArrangement = Arrangement.spacedBy(6.dp)
//        ) {
//            genreList?.forEach {
//                GenreChip(it)
//            }
//
//        }

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
                        interactionSource = interactionSources[1],
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(size)
                            .animateWidth(interactionSources[1]),
                        onClick = {
//                            onConfirm()
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


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplitButtonWithDropdownMenuSample(seasonData: List<SearchTvEntity>) {
    var checked by remember { mutableStateOf(false) }
    var selectedSeason by remember { mutableIntStateOf(0) }
    Box(
    ) {
        SplitButtonLayout(
            leadingButton = {
                SplitButtonDefaults.TonalLeadingButton(onClick = { /* Do Nothing */ }) {
                    Text(seasonData[selectedSeason].name)
                }
            },
            trailingButton = {
                val description = "Toggle Button"
                TooltipBox(
                    positionProvider =
                        TooltipDefaults.rememberTooltipPositionProvider(
                            TooltipAnchorPosition.Above
                        ),
                    tooltip = { PlainTooltip { Text(description) } },
                    state = rememberTooltipState(),
                ) {
                    SplitButtonDefaults.TonalTrailingButton(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        modifier =
                            Modifier.semantics {
                                stateDescription = if (checked) "Expanded" else "Collapsed"
                                contentDescription = description
                            },
                    ) {
                        val rotation: Float by
                        animateFloatAsState(
                            targetValue = if (checked) 180f else 0f,
                            label = "Trailing Icon Rotation",
                        )
                        Box(
                            modifier =
                                Modifier
                                    .size(SplitButtonDefaults.TrailingIconSize)
                                    .graphicsLayer {
                                        this.rotationZ = rotation
                                    },
                        ) {
                            Symbol(R.drawable.close_24px)

                        }

                    }
                }
            },
        )
        DropdownMenu(
            expanded = checked,
            onDismissRequest = { checked = false },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = RoundedCornerShape(Radius.Large),
        ) {


            seasonData.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    shapes = MenuItemShapes(
                        MenuDefaults.standaloneItemShape,
                        MenuDefaults.selectedItemShape
                    ),
                    colors = MenuDefaults.selectableItemColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    selected = index == selectedSeason,
                    checkedLeadingIcon = {
                        Symbol(
                            R.drawable.check_24px,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    },
                    text = {
                        Text(
                            item.name,
                            color = if (index == selectedSeason) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        selectedSeason = index
                        checked = false
                    },
                )

            }
        }
    }
}
