package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pranshulgg.watchmaster.model.WatchStatus
import java.time.Instant

@Entity(tableName = "watchlist")
data class WatchlistItemEntity(
    @PrimaryKey val id: Long,

    val mediaType: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val genreIds: List<Int>?,
    val releaseDate: String?,

    val status: WatchStatus = WatchStatus.WANT_TO_WATCH,

    val addedDate: Instant,
    val startedDate: Instant? = null,
    val finishedDate: Instant? = null,
    val interruptedAt: Instant? = null,

    val avgRating: Double? = null,

    val notes: String? = null
)
