package com.pranshulgg.watchmaster.data


import androidx.room.Embedded
import androidx.room.Relation
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistTvEntity

data class WatchlistWithSeasons(
    @Embedded val item: WatchlistItemEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "watchlistId"
    )
    val seasons: List<WatchlistTvEntity>
)
