package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.CountryWatchProviders
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
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
import kotlin.coroutines.cancellation.CancellationException


@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val repo: TvRepository
) : ViewModel() {

    var state by mutableStateOf<TvBundle?>(null)
        private set

    var loading by mutableStateOf(false)
        private set


    fun load(tvId: Long, onBack: () -> Unit) {
        if (state != null) return

        viewModelScope.launch {
            loading = true

            val tv = try {
                repo.getWholeTvData(tvId)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                SnackbarManager.show("Failed to fetch season data")
                onBack()
                return@launch
            }

            state = tv

            loading = false
        }
    }

    fun loadEpisodes(
        tvId: Long,
        seasonId: Long,
        seasonNumber: Int
    ) {
        viewModelScope.launch {
            try {
                repo.ensureEpisodesFetched(tvId, seasonId, seasonNumber)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                SnackbarManager.show(
                    "Failed to fetch season data",
                    actionLabel = "Retry",
                    onAction = {
                        loadEpisodes(tvId, seasonId, seasonNumber)
                    }
                )
            }
        }
    }

    fun refreshSeasonData(season: SeasonEntity) {
        loading = true
        viewModelScope.launch {
            try {
                repo.refreshSeasonData(season)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                SnackbarManager.show(
                    "Refresh failed",
                )
            }
            loading = false
            loadEpisodes(season.showId, season.seasonId, season.seasonNumber)
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
            _uiState.value.copy(isWatchProviderSheetOpen = false)
    }

    fun markEpWatched(
        epId: Long,
        seasonId: Long,
        episodeNumber: Int
    ) {
        viewModelScope.launch {
            repo.markEpWatched(epId, seasonId, episodeNumber)
        }
    }

    fun markEpUnWatched(
        epId: Long,
        seasonId: Long,
        episodeNumber: Int
    ) {
        viewModelScope.launch {
            repo.markEpUnWatched(epId, seasonId, episodeNumber)
        }
    }

    fun markAllEpsWatched(seasonId: Long, lastEpNumber: Int) {
        viewModelScope.launch {
            repo.markAllEpWatched(seasonId, lastEpNumber)
        }
    }

    fun markEpWatchedFromCount(seasonId: Long, count: Int) {
        viewModelScope.launch {
            repo.markEpWatchedFromCount(seasonId, count)
        }
    }

}