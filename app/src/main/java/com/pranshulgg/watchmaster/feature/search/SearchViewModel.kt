package com.pranshulgg.watchmaster.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    var results by mutableStateOf<List<SearchItem>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var showNoResultsSnack by mutableStateOf(false)
        private set

    var seasonData by mutableStateOf<List<TvSeasonDto>>(emptyList())
        private set

    var seasonLoading by mutableStateOf(false)
        private set


    fun onQueryChange(q: String) {
        query = q
    }

    private val seasonCache = mutableMapOf<Long, List<TvSeasonDto>>()


    fun search(type: SearchType = SearchType.MULTI) {
        if (query.isBlank()) return

        viewModelScope.launch {
            loading = true
            val res = repo.search(query, type)
            results = res
            loading = false

            showNoResultsSnack = res.isEmpty()
        }
    }


    fun fetchSeasonData(tvId: Long) {
        seasonCache[tvId]?.let {
            seasonData = it
            return
        }

        viewModelScope.launch {
            seasonLoading = true
            val data = repo.getSeasonData(tvId)
            seasonCache[tvId] = data
            seasonData = data
            seasonLoading = false
        }
    }

    fun addToWatchlist(item: SearchItem, tvDetails: List<TvSeasonDto>? = null) {
        viewModelScope.launch {
            watchlistRepository.addFromSearch(item, tvDetails)
        }
    }

    fun addSeasonToWatchlist(id: Long, tvDetails: List<TvSeasonDto>) {
        viewModelScope.launch {
            watchlistRepository.insertSeason(id, tvDetails)
        }
    }
}
