package com.pranshulgg.watchmaster.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class WatchlistViewModel(
    repository: WatchlistRepository
) : ViewModel() {

    val watchlist = repository
        .getWatchlist()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
