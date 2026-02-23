package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "watchlist_tv",
    foreignKeys = [
        ForeignKey(
            entity = WatchlistItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["watchlistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("watchlistId")]
)
data class WatchlistTvEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val watchlistId: Long,

    val seasonNumber: Int = 0,
    val episodeCount: Int = 0,
    val name: String,
    val posterPath: String?,
    val airDate: String?
)
