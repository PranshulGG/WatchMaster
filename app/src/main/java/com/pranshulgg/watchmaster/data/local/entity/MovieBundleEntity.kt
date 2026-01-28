package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_bundle")
data class MovieBundleEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val runtime: Int?,
    val genresJson: String,
    val creditsJson: String,
    val videosJson: String,
    val imagesJson: String,
    val watchProvidersJson: String?,
    val similarJson: String,
    val recommendationsJson: String,
    val reviewsJson: String,
    val releaseDatesJson: String,
    val cachedAt: Long
)
