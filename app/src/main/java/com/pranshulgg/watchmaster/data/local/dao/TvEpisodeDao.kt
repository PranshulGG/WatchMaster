package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<TvEpisodeEntity>)


    @Query("UPDATE tv_episodes SET isWatched = :watched WHERE epId = :epId")
    suspend fun updateEpisodeStatus(epId: Long, watched: Boolean)

    @Query("SELECT * FROM tv_episodes WHERE seasonId = :seasonId ORDER BY episode_number")
    fun getById(seasonId: Long): Flow<List<TvEpisodeEntity>>

    @Query("SELECT COUNT(*) FROM tv_episodes WHERE seasonId = :seasonId")
    suspend fun countEpisodes(seasonId: Long): Int

}