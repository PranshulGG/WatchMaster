package com.pranshulgg.watchmaster.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeNavViewModel : ViewModel() {

    var currentDefaultSelectedTab = "home"
    var selectedTab = when (currentDefaultSelectedTab) {
        "home" -> 0
        "movies" -> 1
        "tv_series" -> 2
        else -> 0
    }
    var selectedItem by mutableIntStateOf(selectedTab)
}