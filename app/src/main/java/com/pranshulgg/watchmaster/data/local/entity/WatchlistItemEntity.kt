package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistItemEntity(
    @PrimaryKey
    val id: Long,

    val mediaType: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val genreIds: List<Int>?, // 👈 stays EXACTLY like API
    val releaseDate: String?,

    val watched: Boolean = false,
    val finished: Boolean = false,
    val interrupted: Boolean = false,
    val wantToWatch: Boolean = true,

    val interruptedAt: Long? = null,
    val notes: String? = null
)
