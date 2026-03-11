package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_episodes",
    foreignKeys = [
        ForeignKey(
            entity = SeasonEntity::class,
            parentColumns = ["seasonId"],
            childColumns = ["seasonId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("seasonId")]
)
data class TvEpisodeEntity(

    @PrimaryKey(autoGenerate = true)
    val epId: Long = 0,
    val seasonId: Long,
    val showId: Long,
    val air_date: String?,
    val episode_number: Int,
    val name: String,
    val overview: String?,
    val still_path: String?,
    val runtime: Int?,
    val isWatched: Boolean = false
)
