package com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.mapper.toIcon
import com.pranshulgg.watchmaster.feature.movie.lists.MovieListsViewModel
import com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.ui.MovieListEntrySheet
import com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.ui.MovieListSelectIconSheet
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import kotlinx.coroutines.launch


data class MovieListEntryUiState(
    val isSheetOpen: Boolean = false,
    val listName: String = "",
    val listDescription: String = "",
    val listIcon: MediaListsIcons = MediaListsIcons.FOLDER,
    val listMoviesList: List<WatchlistItemEntity> = emptyList(),
    val isSelectListIconSheetOpen: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListEntryScreen(id: Long = -1L, navController: NavController) {

    val viewModel: MovieListsViewModel = hiltViewModel()

    LaunchedEffect(id) {
        if (id != -1L) {
            viewModel.getMovieListById(id)
        }
    }

    val uiState = viewModel.uiState.value
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
    val itemsFlow by watchlistViewModel.watchlist.collectAsState()
    val items = itemsFlow.filter { it.mediaType == "movie" }
    val isLoading by watchlistViewModel.isLoading.collectAsState()
    val currentMovieList by viewModel.currentMovieList.collectAsState(initial = null)

    val currentListMoviesFiltered = remember(currentMovieList, items) {
        val list = currentMovieList
        if (list == null) emptyList()
        else items.filter { list.movieIds.contains(it.id) }
    }


    LaunchedEffect(currentListMoviesFiltered) {
        val item = currentMovieList
        if (item != null) {
            viewModel.updateListName(item.name)
            viewModel.updateListDescription(item.description)
            viewModel.updateList(currentListMoviesFiltered)
            viewModel.updateListIcon(item.icon ?: MediaListsIcons.FOLDER)
        }
    }


    LargeTopBarScaffold(
        title = if (id != 1L) "Update movie list" else "Create movie list",
        navigationIcon = { NavigateUpBtn(navController) },
    ) { paddingValues ->

        MovieListEntryContent(
            paddingValues,
            listNameText = uiState.listName,
            listDescriptionText = uiState.listDescription,
            onNameChange = { viewModel.updateListName(it) },
            onDescriptionChange = { viewModel.updateListDescription(it) },
            onSave = { updatingList ->
                viewModel.saveList(updatingList, id)
                navController.popBackStack()
            },
            onAddMovie = { viewModel.showMovieListSheet() },
            selectedMovieList = uiState.listMoviesList,
            onSelectIcon = { viewModel.showSelectListIconSheet() },
            selectedListIcon = uiState.listIcon.toIcon(),
            isEditingList = id != -1L,
        )
    }


    MovieListEntrySheet(viewModel, sheetState, items, isLoading)
    MovieListSelectIconSheet(viewModel, sheetState)
}