package com.pranshulgg.watchmaster.feature.tv

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvHomeViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(TvHomeUiState())
    val uiState: State<TvHomeUiState> = _uiState


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
            isUpdateRating = false,
            originalRating = 0f
        )
    }

    fun showBottomSheet(item: SeasonEntity?, watchlistItem: WatchlistItemEntity) {
        _uiState.value = _uiState.value.copy(
            actionSheetWatchlistItem = watchlistItem,
            actionSheetSeasonItem = item,
            isSheetOpen = true,
            seriesId = null
        )
    }

    fun hideBottomSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = false)
    }

    fun showConfirmationDialog(seriesId: Long? = null) {
        _uiState.value =
            _uiState.value.copy(
                showConfirmationDialog = true,
                seriesId = seriesId
            )
    }

    fun showStatusConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showStatusConfirmationDialog = true)
    }

    fun hideStatusConfirmationDialog() {
        _uiState.value = _uiState.value.copy(showStatusConfirmationDialog = false)
    }

    fun hideConfirmationDialog() {
        _uiState.value = _uiState.value.copy(
            showConfirmationDialog = false,
            seriesId = null
        )
    }
}