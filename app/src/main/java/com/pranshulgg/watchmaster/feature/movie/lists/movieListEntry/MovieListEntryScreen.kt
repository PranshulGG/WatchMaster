package com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry

import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.ui.MovieListEntrySheet
import com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.ui.MovieListSelectIconDialog
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListEntryScreen(navController: NavController) {

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
        MovieListEntryContent(
            paddingValues,
            listNameText = uiState.listName,
            listDescriptionText = uiState.listDescription,
            onNameChange = { viewModel.updateListName(it) },
            onDescriptionChange = { viewModel.updateListDescription(it) },
            onSave = {
                if (uiState.listMoviesList.isNotEmpty()) {
                    viewModel.saveList()
                    navController.popBackStack()
                } else {
                    SnackbarManager.show("List must have at least one movie")
                }
            },
            onAddMovie = { viewModel.showMovieListSheet() },
            selectedMovieList = uiState.listMoviesList,
            onSelectIcon = { viewModel.showSelectListIconDialog() }
        )

    }



    MovieListEntrySheet(viewModel, sheetState, items, isLoading)
    MovieListSelectIconDialog(viewModel)
}