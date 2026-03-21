package com.pranshulgg.watchmaster.feature.movie.lists.create.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.LoadingPlaceholder
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.lists.MovieListsViewModel
import com.pranshulgg.watchmaster.feature.movie.lists.create.components.MovieListsSheetContent
import okio.Options

private data class Option(
    val status: WatchStatus,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListsSheet(
    viewModel: MovieListsViewModel,
    sheetState: SheetState,
    items: List<WatchlistItemEntity>,
    isLoading: Boolean
) {
    val uiState = viewModel.uiState.value
    val options = listOf(
        Option(WatchStatus.WANT_TO_WATCH, "Watching"),
        Option(WatchStatus.WATCHING, "Watching"),
        Option(WatchStatus.FINISHED, "Finished")
    )
    var selected by remember { mutableStateOf(WatchStatus.WANT_TO_WATCH) }
    val selectedMovies = remember { mutableStateListOf<WatchlistItemEntity>() }


    val filteredItems = items.filter { it.status == selected }

    if (uiState.isSheetOpen)
        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = { viewModel.hideMovieListSheet() },
            onConfirm = {
                viewModel.updateList(selectedMovies)
                viewModel.hideMovieListSheet()
            },
            confirmText = "Confirm",
            confirmBtnMaxWidth = true
        ) {

            Column() {
                if (!isLoading) {
                    StatusTabs(options, selected, onSelected = { selected = it })

                    MovieListsSheetContent(
                        filteredItems,
                        onMovieSelect = { selectedMovies.add(it) },
                        onMovieRemove = { selectedMovies.remove(it) }
                    )
                } else {
                    LoadingPlaceholder()
                }
            }
        }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun StatusTabs(
    options: List<Option>,
    selected: WatchStatus,
    onSelected: (WatchStatus) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        options.forEach { option ->
            ToggleButton(
                checked = option.status == selected,
                onCheckedChange = {
                    onSelected(option.status)
                },
                modifier = Modifier.semantics { role = Role.RadioButton },
                shapes = ToggleButtonDefaults.shapes(),
                colors = ToggleButtonDefaults.toggleButtonColors(
                    checkedContainerColor = MaterialTheme.colorScheme.tertiary,
                    checkedContentColor = MaterialTheme.colorScheme.onTertiary,
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                )
            ) {
                Text(option.label)
            }
        }
    }

}