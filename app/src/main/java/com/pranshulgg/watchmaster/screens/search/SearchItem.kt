package com.pranshulgg.watchmaster.screens.search

data class SearchItem(
    val id: Long,
    val mediaType: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val genreIds: List<Int>? = null,
    val releaseDate: String? = null,
    val originalLanguage: String? = "English",
    val avg_rating: Double? = null
)
