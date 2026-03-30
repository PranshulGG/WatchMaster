package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.MovieListsRepository
import com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.MovieListEntryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListsViewModel @Inject constructor(
    private val repo: MovieListsRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(MovieListEntryUiState())
    val uiState: State<MovieListEntryUiState> = _uiState

    private val _currentMovieList = MutableStateFlow<MovieListsEntity?>(null)
    val currentMovieList: StateFlow<MovieListsEntity?> = _currentMovieList


    val movieLists = repo
        .getMovieLists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun saveList() = viewModelScope.launch {

        val item = MovieListsEntity(
            name = uiState.value.listName,
            description = uiState.value.listDescription,
            movieIds = uiState.value.listMoviesList.map { it.id },
            icon = uiState.value.listIcon
        )

        repo.insertMovieListsItem(item)
    }

    fun getMovieListById(id: Long) {
        viewModelScope.launch {
            _currentMovieList.value = repo.getMovieListById(id)
        }
    }


    fun delete(id: Long) = viewModelScope.launch {
        repo.deleteMovieListsItem(id)
    }

    fun showMovieListSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = true)
    }

    fun hideMovieListSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = false)
    }

    fun updateListName(name: String) {
        _uiState.value = _uiState.value.copy(listName = name)
    }

    fun updateListDescription(description: String) {
        _uiState.value = _uiState.value.copy(listDescription = description)
    }

    fun updateListIcon(icon: MediaListsIcons) {
        _uiState.value = _uiState.value.copy(listIcon = icon)
    }


    fun updateList(list: List<WatchlistItemEntity>) {
        _uiState.value = _uiState.value.copy(listMoviesList = list)
    }

    fun showSelectListIconSheet() {
        _uiState.value = _uiState.value.copy(isSelectListIconSheetOpen = true)
    }


    fun hideSelectListIconSheet() {
        _uiState.value = _uiState.value.copy(isSelectListIconSheetOpen = false)
    }
}