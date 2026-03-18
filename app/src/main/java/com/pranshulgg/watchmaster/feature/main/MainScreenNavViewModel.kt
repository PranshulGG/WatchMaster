package com.pranshulgg.watchmaster.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainScreenNavViewModel(defaultTab: String) : ViewModel() {
    val selectedTab = when (defaultTab) {
        "Home" -> 0
        "Movies" -> 1
        "Tv series" -> 2
        else -> 0
    }

    var selectedItem by mutableIntStateOf(selectedTab)
}