package com.pranshulgg.watchmaster.feature.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchlistRepository
) : ViewModel() {


    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val watchlist = repository
        .getWatchlist()
        .onEach {
            _isLoading.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
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

    fun setNote(id: Long, note: String) = viewModelScope.launch {
        repository.updateNote(id, note)
    }

    private val _currentItem = MutableStateFlow<WatchlistItemEntity?>(null)
    val currentItem = _currentItem.asStateFlow()

    fun observeItem(id: Long) {
        viewModelScope.launch {
            repository.getItemById(id)
                .distinctUntilChanged()
                .collect { _currentItem.value = it }
        }
    }

    fun seasonsForShow(showId: Long): Flow<List<SeasonEntity>> {
        return repository.getSeasonsForShow(showId)
    }

    fun startSeason(id: Long) = viewModelScope.launch {
        repository.markSeasonStarted(id)
    }

    fun finishSeason(id: Long) = viewModelScope.launch {
        repository.markSeasonFinished(id)
    }

    fun interruptSeason(id: Long) = viewModelScope.launch {
        repository.markSeasonInterrupted(id)
    }

    fun resetSeason(id: Long) = viewModelScope.launch {
        repository.markSeasonWantToWatch(id)
    }


    fun setSeasonUserRating(id: Long, rating: Double) = viewModelScope.launch {
        repository.updateSeasonUserRating(id, rating)
    }

    fun setSeasonNote(id: Long, note: String) = viewModelScope.launch {
        repository.updateSeasonNote(id, note)
    }

    fun deleteSeason(id: Long) = viewModelScope.launch {
        repository.deleteSeason(id)
    }
}
