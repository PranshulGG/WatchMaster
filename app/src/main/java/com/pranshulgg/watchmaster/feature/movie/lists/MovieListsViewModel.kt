package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import com.pranshulgg.watchmaster.data.repository.MovieListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListsViewModel @Inject constructor(
    private val repo: MovieListsRepository
) : ViewModel() {


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
            movieIds = uiState.value.listMovieIds
        )

        repo.insertMovieListsItem(item)
    }


    fun delete(id: Long) = viewModelScope.launch {
        repo.deleteMovieListsItem(id)
    }


    private val _uiState = mutableStateOf(MovieListsUiState())
    val uiState: State<MovieListsUiState> = _uiState

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

    fun updateList(list: List<Long>) {
        _uiState.value = _uiState.value.copy(listMovieIds = list)
    }
}