package com.pranshulgg.watchmaster.core.model

data class SeasonData(
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int,
    val airDate: String?,
    val posterPath: String?
)