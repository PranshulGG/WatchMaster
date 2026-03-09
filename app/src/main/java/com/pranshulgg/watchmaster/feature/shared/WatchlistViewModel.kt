package com.pranshulgg.watchmaster.feature.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.distinctUntilChangedBy
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

    val seasons = repository
        .getAllSeasons()
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


    fun item(id: Long): StateFlow<WatchlistItemEntity?> {
        return repository.getItemById(id)
            .distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
    }

    private val seasonsCache = mutableMapOf<Long, StateFlow<List<SeasonEntity>>>()

    fun seasonsForShow(showId: Long): StateFlow<List<SeasonEntity>> {
        return seasonsCache.getOrPut(showId) {
            repository.getSeasonsForShow(showId)
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    emptyList()
                )
        }
    }


    fun startSeason(seasonId: Long) = viewModelScope.launch {
        repository.markSeasonStarted(seasonId)
    }

    fun finishSeason(seasonId: Long) = viewModelScope.launch {
        repository.markSeasonFinished(seasonId)
    }

    fun interruptSeason(seasonId: Long) = viewModelScope.launch {
        repository.markSeasonInterrupted(seasonId)
    }

    fun resetSeason(seasonId: Long) = viewModelScope.launch {
        repository.markSeasonWantToWatch(seasonId)
    }

    fun setSeasonUserRating(seasonId: Long, rating: Double) =
        viewModelScope.launch {
            repository.updateSeasonUserRating(seasonId, rating)
        }

    fun setSeasonNote(seasonId: Long, note: String) =
        viewModelScope.launch {
            repository.updateSeasonNote(seasonId, note)
        }

    fun deleteSeason(seasonId: Long) = viewModelScope.launch {
        repository.deleteSeason(seasonId)
    }
}
