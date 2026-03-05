package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val repo: TvRepository
) : ViewModel() {

    var state by mutableStateOf<TvBundle?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    fun load(tvId: Long, seasonNumber: Int) {
        if (state != null) return

        viewModelScope.launch {
            loading = true
            state = repo.getWholeTvData(tvId, seasonNumber)
            loading = false
        }
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

}