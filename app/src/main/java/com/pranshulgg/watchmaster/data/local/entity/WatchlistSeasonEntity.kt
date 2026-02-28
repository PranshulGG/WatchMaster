package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_seasons", foreignKeys = [
        ForeignKey(
            entity = WatchlistItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("showId")]
)
data class WatchlistSeasonEntity(
    @PrimaryKey(autoGenerate = true)
    val seasonId: Long = 0,
    val showId: Long,
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int,
    val airDate: String?,
    val posterPath: String?
)
