package com.pranshulgg.watchmaster.core.model

import com.google.gson.annotations.SerializedName

data class Trending(
    val id: Long,
    val title: String?,
    val name: String?,
    val backdropPath: String,
    val posterPath: String,
    val mediaType: String,
    val releaseDate: String?,
    val firstAirDate: String?,
    val avgRating: Double?,
    val overview: String,
    val genreIds: List<Int>?,
    val trendingType: String

)