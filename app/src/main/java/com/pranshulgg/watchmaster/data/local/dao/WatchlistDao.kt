package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WatchlistItemEntity)

    @Query("SELECT * FROM watchlist ORDER BY id DESC")
    fun getAll(): Flow<List<WatchlistItemEntity>>
}
