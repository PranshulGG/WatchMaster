package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.local.entity.WatchlistSeasonEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface SeasonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<WatchlistSeasonEntity>)

    @Query(
        """
        SELECT * FROM tv_seasons
        WHERE showId = :showId
        ORDER BY seasonNumber
    """
    )
    fun getSeasonForShow(showId: Long): Flow<List<WatchlistSeasonEntity>>

    @Query("DELETE FROM tv_seasons WHERE showId = :showId")
    suspend fun deleteForShow(showId: Long)

    @Query(
        """
        UPDATE tv_seasons
        SET status = :status,
            seasonStartedDate = :started,
            seasonFinishedDate = :finished,
            seasonInterruptedAt = :interruptedAt
        WHERE showId = :id
    """
    )
    suspend fun updateSeasonStatus(
        id: Long,
        status: WatchStatus,
        started: Instant? = null,
        finished: Instant? = null,
        interruptedAt: Instant? = null
    )

}