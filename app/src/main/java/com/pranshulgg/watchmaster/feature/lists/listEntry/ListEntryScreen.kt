package com.pranshulgg.watchmaster.feature.lists.listEntry

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.tiles.M3eButton
import com.pranshulgg.watchmaster.core.ui.components.tiles.M3eOutlinedButton
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.mapper.toIcon
import com.pranshulgg.watchmaster.feature.lists.ListsViewModel
import com.pranshulgg.watchmaster.feature.lists.listEntry.ui.ListEntrySelectIconSheet
import com.pranshulgg.watchmaster.feature.lists.listEntry.ui.ListEntrySheet
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel


data class ListEntryScreenUiState(
    val isSheetOpen: Boolean = false,
    val listName: String = "",
    val listDescription: String = "",
    val listIcon: MediaListsIcons = MediaListsIcons.FOLDER,
    val selectedMediaItems: List<WatchlistItemEntity> = emptyList(),
    val isSelectListIconSheetOpen: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ListEntryScreen(id: Long = -1L, navController: NavController) {

    val viewModel: ListsViewModel = hiltViewModel()

    LaunchedEffect(id) {
        if (id != -1L) {
            viewModel.getCustomListById(id)
        }
    }

    val uiState = viewModel.uiState.value
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
    val items by watchlistViewModel.watchlist.collectAsState()
    val isLoading by watchlistViewModel.isLoading.collectAsState()
    val currentMovieList by viewModel.currentList.collectAsState(initial = null)

    val currentListItemsFiltered = remember(currentMovieList, items) {
        val list = currentMovieList
        if (list == null) emptyList()
        else items.filter { list.ids.contains(it.id) }
    }


    LaunchedEffect(currentMovieList) {
        val item = currentMovieList
        if (item != null) {
            viewModel.updateListName(item.name)
            viewModel.updateListDescription(item.description)
            viewModel.updateList(currentListItemsFiltered)
            viewModel.updateListIcon(item.icon ?: MediaListsIcons.FOLDER)
        }
    }


    LargeTopBarScaffold(
        title = if (id != -1L) "Update list" else "Create list",
        navigationIcon = { NavigateUpBtn(navController) },
        bottomBar = {
            val size = ButtonDefaults.MediumContainerHeight

            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    M3eButton(
                        text = if (id != -1L) "Update list" else "Save list",
                        onClick = {
                            viewModel.saveList(id != -1L, id)
                            SnackbarManager.show("List saved")
                            navController.popBackStack()
                        },
                        size = size,
                        icon = R.drawable.check_24px,
                        modifier = Modifier.weight(1f),
                        disabled = uiState.listName.isEmpty()
                    )

                    Gap(horizontal = 10.dp)

                    M3eOutlinedButton(
                        text = "Add",
                        onClick = viewModel::showCustomListScreenSheet,
                        icon = R.drawable.add_24px,
                        size = size
                    )
                }
            }
        }
    ) { paddingValues ->

        ListEntryContent(
            paddingValues,
            listNameText = uiState.listName,
            listDescriptionText = uiState.listDescription,
            onNameChange = { viewModel.updateListName(it) },
            onDescriptionChange = { viewModel.updateListDescription(it) },
            selectedMovieList = uiState.selectedMediaItems,
            onSelectIcon = { viewModel.showSelectListIconSheet() },
            selectedListIcon = uiState.listIcon.toIcon(),
        )
    }


    ListEntrySheet(viewModel, sheetState, items, isLoading)
    ListEntrySelectIconSheet(viewModel, sheetState)
}