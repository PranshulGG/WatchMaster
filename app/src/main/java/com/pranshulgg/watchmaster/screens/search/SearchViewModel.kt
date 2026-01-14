package com.pranshulgg.watchmaster.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: SearchRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    var results by mutableStateOf<List<SearchItem>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    fun onQueryChange(q: String) {
        query = q
    }

    fun search() {
        if (query.isBlank()) return

        viewModelScope.launch {
            loading = true
            results = repo.search(query)
            loading = false
        }
    }
}
