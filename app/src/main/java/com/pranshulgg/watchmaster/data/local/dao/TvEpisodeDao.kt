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

    @Query("UPDATE tv_episodes SET isWatched = 1 WHERE seasonId = :seasonId")
    suspend fun markAllEpWatched(seasonId: Long)

    @Query("UPDATE tv_episodes SET isWatched = 0 WHERE seasonId = :seasonId")
    suspend fun markAllEpUnWatched(seasonId: Long)

    @Query("SELECT * FROM tv_episodes WHERE seasonId = :seasonId ORDER BY episode_number")
    fun getEpisodesForSeason(seasonId: Long): Flow<List<TvEpisodeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM tv_episodes WHERE seasonId = :seasonId)")
    suspend fun hasEpisodes(seasonId: Long): Boolean

    @Query("SELECT * FROM tv_episodes")
    suspend fun getAllEpisodes(): List<TvEpisodeEntity>

    @Query("DELETE FROM tv_episodes")
    suspend fun clearAll()

}