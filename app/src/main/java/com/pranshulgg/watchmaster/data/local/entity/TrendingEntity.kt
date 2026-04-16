package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi


@Entity(tableName = "trending_data")
data class TrendingEntity(
    @PrimaryKey
    val mainUuid: String,

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

    val cachedAt: Long,
    val trendingType: String,
)


