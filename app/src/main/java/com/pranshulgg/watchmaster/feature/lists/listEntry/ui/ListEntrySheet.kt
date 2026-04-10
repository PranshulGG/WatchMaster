package com.pranshulgg.watchmaster.feature.lists.listEntry.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.LoadingPlaceholder
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.lists.ListsViewModel
import com.pranshulgg.watchmaster.feature.lists.listEntry.components.ListEntrySheetContent

private enum class SelectedTab(val type: String) {
    MOVIES("movie"),
    TV_SERIES("tv")
}

private data class Option(
    val tab: SelectedTab,
    val label: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListEntrySheet(
    viewModel: ListsViewModel,
    sheetState: SheetState,
    items: List<WatchlistItemEntity>,
    isLoading: Boolean,
) {
    val uiState = viewModel.uiState.value

    val options = listOf(
        Option(SelectedTab.MOVIES, "Movies"),
        Option(SelectedTab.TV_SERIES, "TV series")
    )

    var selected by remember { mutableStateOf(SelectedTab.MOVIES) }
    val filteredItems = items.filter { it.mediaType == selected.type }

    if (uiState.isSheetOpen) {

        val selectedMovies = rememberSaveable {
            mutableStateListOf<WatchlistItemEntity>().apply {
                addAll(uiState.selectedMediaItems)
            }
        }

        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = { viewModel.hideCustomListScreenSheet() },
            onConfirm = {
            },
            showActions = false,
            removeBottomInset = true
        ) { hide ->

            Column() {
                if (!isLoading) {
                    StatusTabs(options, selected, onSelected = { selected = it })
                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                    ListEntrySheetContent(
                        filteredItems,
                        onMovieSelect = {
                            if (selectedMovies.contains(it)) {
                                selectedMovies.remove(it)
                            } else {
                                selectedMovies.add(it)
                            }
                        },
                        selectedMovies,
                        onConfirm = {
                            viewModel.updateList(selectedMovies)
                            hide()
                        },
                        onCancel = {
                            hide()
                        }
                    )
                } else {
                    LoadingPlaceholder()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun StatusTabs(
    options: List<Option>,
    selected: SelectedTab,
    onSelected: (SelectedTab) -> Unit,
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            6.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        options.forEach { option ->
            ToggleButton(
                checked = option.tab == selected,
                onCheckedChange = {
                    onSelected(option.tab)
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