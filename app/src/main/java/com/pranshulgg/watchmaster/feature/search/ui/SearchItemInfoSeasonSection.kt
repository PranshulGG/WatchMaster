package com.pranshulgg.watchmaster.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.SettingSection
import com.pranshulgg.watchmaster.core.ui.components.SettingTile
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchItemInfoSeasonSection(
    seasonData: List<TvSeasonDto>,
    onSelectedSeason: (List<TvSeasonDto>?) -> Unit,
    watchlistViewModel: WatchlistViewModel,
    id: Long
) {
    var selectedSeason by remember { mutableIntStateOf(0) }
    val size = ButtonDefaults.MediumContainerHeight
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var seasonChanged by remember { mutableStateOf(false) }
    val totalSeasons = remember { mutableStateListOf<TvSeasonDto>() }
    var isSheetOpen by remember { mutableStateOf(false) }


    val savedSeasons by watchlistViewModel
        .seasonsForShow(id)
        .collectAsState(initial = emptyList())


    val filteredSeasons = remember(seasonData) {
        seasonData.filter { it.name != "Specials" }
    }


    LaunchedEffect(savedSeasons) {
        if (!seasonChanged) {
            val firstUnsaved = filteredSeasons.firstOrNull { season ->
                savedSeasons.none { it.name == season.name }
            }

            selectedSeason = filteredSeasons.indexOf(firstUnsaved ?: -1)

            if (firstUnsaved != null) {
                onSelectedSeason(listOf(firstUnsaved))
            } else {
                onSelectedSeason(null)
            }

        }
    }


    // BUTTON

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
                if (selectedSeason != -1)
                    isSheetOpen = true
            },
            contentPadding = ButtonDefaults.contentPaddingFor(size)
        ) {
            Symbol(
                R.drawable.list_alt_24px,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                size = ButtonDefaults.iconSizeFor(size)
            )
            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
            Text(
                if (selectedSeason == -1) "All season saved" else filteredSeasons[selectedSeason].name,
                style = ButtonDefaults.textStyleFor(size),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


    // SHEET

    if (isSheetOpen)
        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = {
                isSheetOpen = false
            },
            onConfirm = {

            },
            showActions = false,
        ) { hide ->
            LazyColumn() {
                item {
                    SettingSection(
                        title = "All seasons",
                        isModalOption = true,
                        tiles = filteredSeasons.mapIndexed { index, item ->

                            val seasonExists = savedSeasons.any { it.name == item.name }

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
                                        hide()
                                    }
                                },
                                trailing = {
                                    Surface(
                                        color = if (index == selectedSeason) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceBright,
                                        shape = RoundedCornerShape(if (index == selectedSeason) ShapeRadius.Small else ShapeRadius.Full)
                                    ) {
                                        Text(
                                            item.episode_count.toString() + " episodes ${if (seasonExists) "• Saved" else ""}",
                                            modifier = Modifier.padding(
                                                horizontal = 10.dp,
                                                vertical = 5.dp
                                            ),
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
        }
}