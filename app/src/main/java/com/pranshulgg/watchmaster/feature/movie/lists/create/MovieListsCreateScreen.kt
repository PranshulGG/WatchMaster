package com.pranshulgg.watchmaster.feature.movie.lists.create

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.movie.lists.MovieListsViewModel
import com.pranshulgg.watchmaster.feature.movie.lists.create.ui.MovieListsSheet
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListsCreateScreen(navController: NavController) {

    val viewModel: MovieListsViewModel = hiltViewModel()
    val uiState = viewModel.uiState.value
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
    val itemsFlow by watchlistViewModel.watchlist.collectAsState()
    val items = itemsFlow.filter { it.mediaType == "movie" }
    val isLoading by watchlistViewModel.isLoading.collectAsState()

    LargeTopBarScaffold(
        title = "Create movie list",
        navigationIcon = { NavigateUpBtn(navController) }
    ) { paddingValues ->
        MovieListsCreateContent(
            paddingValues,
            listNameText = uiState.listName,
            listDescriptionText = uiState.listDescription,
            onNameChange = { viewModel.updateListName(it) },
            onDescriptionChange = { viewModel.updateListDescription(it) },
            onSave = {
                if (uiState.listMovieIds.isNotEmpty()) {
                    viewModel.saveList()
                    navController.popBackStack()
                } else {
                    SnackbarManager.show("List must have at least one movie")
                }
            },
            onAddMovie = { viewModel.showMovieListSheet() }
        )
    }



    MovieListsSheet(viewModel, sheetState, items, isLoading)

}