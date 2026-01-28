package com.pranshulgg.watchmaster.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import kotlinx.coroutines.launch
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.network.MovieBundleDto

class MovieDetailsViewModel(
    private val repo: MovieRepository
) : ViewModel() {

    var state by mutableStateOf<MovieBundle?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    fun load(movieId: Long) {
        if (state != null) return

        viewModelScope.launch {
            loading = true
            state = repo.getWholeMovieData(movieId)
            loading = false
        }
    }
}
