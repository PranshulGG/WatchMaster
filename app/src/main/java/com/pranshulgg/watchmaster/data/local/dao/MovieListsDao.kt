package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import kotlinx.coroutines.flow.Flow
import kotlin.collections.forEach

@Dao
interface MovieListsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieListItem(item: MovieListsEntity)

    @Query("SELECT * FROM movie_lists")
    fun getMovieLists(): Flow<List<MovieListsEntity>>

    @Query("DELETE FROM movie_lists WHERE id = :id")
    suspend fun deleteMovieListsItem(id: Long)

    @Query("SELECT * FROM movie_lists WHERE id = :id LIMIT 1")
    suspend fun getMovieListById(id: Long): MovieListsEntity


    @Query(
        """
    UPDATE movie_lists 
    SET name = :name,
        description = :description,
        movieIds = :movieIds,
        icon = :icon
    WHERE id = :id
"""
    )
    suspend fun updateList(
        id: Long,
        name: String,
        description: String,
        movieIds: List<Long>,
        icon: MediaListsIcons
    )


    @Query("UPDATE movie_lists SET isPinned = :isPinned WHERE id = :id")
    suspend fun setPinned(id: Long, isPinned: Boolean)

    @Query("DELETE FROM movie_lists")
    suspend fun clearAll()

    suspend fun insertAll(items: List<MovieListsEntity>) {
        items.forEach { insertMovieListItem(it) }
    }

}