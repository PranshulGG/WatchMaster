package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State

@HiltViewModel
class MovieListsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(MovieListsUiState())
    val uiState: State<MovieListsUiState> = _uiState

    fun showCreateListSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = true)
    }

    fun hideCreateListSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = false)
    }

    fun updateListName(name: String) {
        _uiState.value = _uiState.value.copy(listName = name)
    }

    fun updateListDescription(description: String) {
        _uiState.value = _uiState.value.copy(listDescription = description)
    }
}