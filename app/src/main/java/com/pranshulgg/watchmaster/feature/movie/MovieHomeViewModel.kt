package com.pranshulgg.watchmaster.feature.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(MovieHomeUiState())
    val uiState: State<MovieHomeUiState> = _uiState


    fun showRatingDialog() {
        _uiState.value = _uiState.value.copy(showRatingDialog = true)
    }

    fun showUpdateRatingDialog(originalRating: Float = 0f) {
        _uiState.value = _uiState.value.copy(
            showRatingDialog = true,
            originalRating = originalRating,
            isUpdateRating = true
        )
    }

    fun hideRatingDialog() {
        _uiState.value = _uiState.value.copy(
            showRatingDialog = false,
            originalRating = 0f,
            isUpdateRating = false
        )
    }

    fun showBottomSheet(item: WatchlistItemEntity) {
        _uiState.value = _uiState.value.copy(actionSheetItem = item, isSheetOpen = true)
    }

    fun hideBottomSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = false)
    }

    fun showConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showConfirmationDialog = true)
    }

    fun showStatusConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showStatusConfirmationDialog = true)
    }

    fun hideStatusConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showStatusConfirmationDialog = false)
    }

    fun hideConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showConfirmationDialog = false)
    }

    fun showDatePicker() {
        _uiState.value = _uiState.value.copy(showDatePicker = true)
    }

    fun hideDatePicker() {
        _uiState.value = _uiState.value.copy(showDatePicker = false)
    }
}