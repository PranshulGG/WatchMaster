package com.pranshulgg.watchmaster.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pranshulgg.watchmaster.core.utils.PreferencesHelper

class MainScreenNavViewModel : ViewModel() {

    val defaultTab = PreferencesHelper.getString("default_tab")

    val selectedTab = when (defaultTab) {
        "Home" -> 0
        "Movies" -> 1
        "TV series" -> 2
        else -> 0
    }

    var selectedItem by mutableIntStateOf(selectedTab)
}