package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.TvCreditsDto
import java.time.Instant

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
data class SeasonEntity(
    @PrimaryKey(autoGenerate = true)
    val seasonId: Long = 0,
    val showId: Long,
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int,
    val airDate: String?,
    val posterPath: String?,
    val status: WatchStatus = WatchStatus.WANT_TO_WATCH,
    val seasonStartedDate: Instant? = null,
    val seasonFinishedDate: Instant? = null,
    val seasonInterruptedAt: Instant? = null,
    val seasonAddedDate: Instant,
    val seasonUserRating: Double? = null,
    val seasonAvgRating: Double? = null,
    val seasonNotes: String? = null,
)


