package com.pranshulgg.watchmaster.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainScreenNavViewModelFactory(
    private val defaultTab: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenNavViewModel(defaultTab) as T
    }
}
