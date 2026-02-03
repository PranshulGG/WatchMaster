package com.pranshulgg.watchmaster.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val repository: WatchlistRepository
) : ViewModel() {

    val watchlist = repository
        .getWatchlist()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun start(id: Long) = viewModelScope.launch {
        repository.markStarted(id)
    }

    fun finish(id: Long) = viewModelScope.launch {
        repository.markFinished(id)
    }

    fun interrupt(id: Long) = viewModelScope.launch {
        repository.markInterrupted(id)
    }

    fun reset(id: Long) = viewModelScope.launch {
        repository.markWantToWatch(id)
    }

    fun delete(id: Long) = viewModelScope.launch {
        repository.deleteItem(id)
    }

    suspend fun exists(id: Long): Boolean =
        repository.itemExists(id)

    fun setFavorite(id: Long, isFavorite: Boolean) = viewModelScope.launch {
        repository.updateFavorite(id, isFavorite)
    }

    fun setPinned(id: Long, isPinned: Boolean) = viewModelScope.launch {
        repository.updatePinned(id, isPinned)
    }

    fun setUserRating(id: Long, rating: Double) = viewModelScope.launch {
        repository.updateUserRating(id, rating)
    }

//    fun getItem(id: Long): StateFlow<WatchlistItemEntity?> =
//        repository.getItemById(id)
//            .distinctUntilChanged()
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.Eagerly,
//                initialValue = null
//            )

    private val _currentItem = MutableStateFlow<WatchlistItemEntity?>(null)
    val currentItem = _currentItem.asStateFlow()

    fun observeItem(id: Long) {
        viewModelScope.launch {
            repository.getItemById(id)
                .distinctUntilChanged()
                .collect { _currentItem.value = it }
        }
    }


}

