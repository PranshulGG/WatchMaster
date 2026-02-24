package com.pranshulgg.watchmaster.model

data class SeasonDataModel(
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int,
    val airDate: String?,
    val posterPath: String?
)
