package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_bundle")
data class TvBundleEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val overview: String,
    val runtime: Int?,
    val season_number: Int,
    val episodeJson: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val genresJson: String,
    val creditsJson: String,
    val watchProvidersJson: String?,
//    val similarJson: String,
//    val recommendationsJson: String,
    val reviewsJson: String,
    val cachedAt: Long
)
