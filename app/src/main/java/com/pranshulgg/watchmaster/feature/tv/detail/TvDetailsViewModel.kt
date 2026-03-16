package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.CountryWatchProviders
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.data.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList


@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val repo: TvRepository
) : ViewModel() {

    var state by mutableStateOf<TvBundle?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    fun load(tvId: Long) {
        if (state != null) return

        viewModelScope.launch {
            loading = true
            state = repo.getWholeTvData(tvId)
            loading = false
        }
    }


    fun loadEpisodes(
        tvId: Long,
        seasonId: Long,
        seasonNumber: Int
    ) {
        viewModelScope.launch {
            repo.ensureEpisodesFetched(tvId, seasonId, seasonNumber)
        }
    }

    fun seasonEpisodes(seasonId: Long): StateFlow<List<TvEpisodeEntity>> {

        return repo
            .getEpisodesForSeason(seasonId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    }

    private val _uiState = mutableStateOf(TvDetailsUiState())
    val uiState: State<TvDetailsUiState> = _uiState

    fun hideNoteDialog() {
        _uiState.value = _uiState.value.copy(showNoteDialog = false)
    }

    fun showNoteDialog(note: String) {
        _uiState.value = _uiState.value.copy(
            showNoteDialog = true,
            note = note
        )
    }

    fun updateNoteText(note: String) {
        _uiState.value = _uiState.value.copy(note = note)
    }

    fun showRatingDialog() {
        _uiState.value = _uiState.value.copy(showRatingDialog = true)
    }

    fun hideRatingDialog() {
        _uiState.value = _uiState.value.copy(showRatingDialog = false)
    }

    fun showConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showConfirmationDialog = true)
    }

    fun hideConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showConfirmationDialog = false)
    }

    fun showWatchProviderSheet(providers: CountryWatchProviders? = null) {
        _uiState.value =
            _uiState.value.copy(isWatchProviderSheetOpen = true, currentWatchProviders = providers)
    }

    fun hideWatchProviderSheet() {
        _uiState.value =
            _uiState.value.copy(isWatchProviderSheetOpen = false, currentWatchProviders = null)
    }

    fun markEpWatched(epId: Long) {
        viewModelScope.launch {
            repo.markEpWatched(epId)
        }
    }

    fun markEpUnWatched(epId: Long) {
        viewModelScope.launch {
            repo.markEpUnWatched(epId)
        }
    }

    fun updateSeasonProgress(seasonId: Long, progress: Int) {
        viewModelScope.launch {
            repo.updateSeasonProgress(seasonId, progress)
        }
    }
}