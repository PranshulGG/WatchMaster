package com.pranshulgg.watchmaster.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.TvRepository
import kotlinx.coroutines.launch

class TvDetailsViewModel(
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
}
