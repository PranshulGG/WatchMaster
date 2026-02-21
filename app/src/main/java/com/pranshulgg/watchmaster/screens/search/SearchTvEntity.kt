package com.pranshulgg.watchmaster.screens.search

import androidx.room.Entity


data class SearchTvEntity(
    val season_number: Int = 0,
    val episode_count: Int = 0,
    val name: String,
    val poster_path: String,
    val air_date: String,
)