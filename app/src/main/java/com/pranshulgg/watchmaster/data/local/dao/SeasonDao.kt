package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface SeasonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonEntity>)

    @Query(
        """
        SELECT * FROM tv_seasons
        WHERE showId = :showId
        ORDER BY seasonNumber
    """
    )
    fun getSeasonForShow(showId: Long): Flow<List<SeasonEntity>>

    @Query("DELETE FROM tv_seasons WHERE seasonId = :seasonId")
    suspend fun deleteForShow(seasonId: Long)

    @Query(
        """
        UPDATE tv_seasons
        SET status = :status,
            seasonStartedDate = :started,
            seasonFinishedDate = :finished,
            seasonInterruptedAt = :interruptedAt
        WHERE seasonId = :seasonId
    """
    )
    suspend fun updateSeasonStatus(
        seasonId: Long,
        status: WatchStatus,
        started: Instant? = null,
        finished: Instant? = null,
        interruptedAt: Instant? = null
    )

    @Query("UPDATE tv_seasons SET seasonUserRating = :rating WHERE seasonId = :seasonId")
    suspend fun updateSeasonUserRating(seasonId: Long, rating: Double)

    @Query("UPDATE tv_seasons SET seasonNotes= :note WHERE seasonId = :seasonId")
    suspend fun setSeasonUserNote(seasonId: Long, note: String)

    @Query("SELECT * FROM tv_seasons")
    fun getAllSeasons(): Flow<List<SeasonEntity>>

    @Query("UPDATE tv_seasons SET seasonProgress = :progress WHERE seasonId = :seasonId")
    suspend fun updateSeasonProgress(seasonId: Long, progress: Int)


    @Query("DELETE FROM tv_seasons")
    suspend fun clearAll()
}