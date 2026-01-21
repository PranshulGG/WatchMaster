package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.model.WatchStatus
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface WatchlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WatchlistItemEntity)

    @Query("SELECT * FROM watchlist ORDER BY id DESC")
    fun getAll(): Flow<List<WatchlistItemEntity>>

    @Query(
        """
        UPDATE watchlist
        SET status = :status,
            startedDate = :started,
            finishedDate = :finished,
            interruptedAt = :interruptedAt
        WHERE id = :id
    """
    )
    suspend fun updateStatus(
        id: Long,
        status: WatchStatus,
        started: Instant? = null,
        finished: Instant? = null,
        interruptedAt: Instant? = null
    )

    @Query("DELETE FROM watchlist WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM watchlist WHERE status = 'FINISHED'")
    suspend fun deleteFinished()

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE id = :id)")
    suspend fun exists(id: Long): Boolean

}
