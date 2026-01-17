package com.pranshulgg.watchmaster.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository

class WatchlistViewModelFactory(
    private val repository: WatchlistRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WatchlistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
