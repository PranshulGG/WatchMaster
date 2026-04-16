package com.pranshulgg.watchmaster.core.network

import com.google.gson.annotations.SerializedName

data class TrendingDayDto(
    val results: List<TrendingItemDto>,
    val trendingType: String = "day"

)


data class TrendingWeekDto(
    val results: List<TrendingItemDto>,
    val trendingType: String = "week"
)

data class TrendingItemDto(
    val id: Long,
    val title: String?,
    val name: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("vote_average")
    val avgRating: Double?,

    val overview: String,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?
)