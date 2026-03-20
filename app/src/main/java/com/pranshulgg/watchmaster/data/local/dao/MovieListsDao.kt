package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieListsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieListItem(item: MovieListsEntity)

    @Query("SELECT * FROM movie_lists")
    fun getMovieLists(): Flow<List<MovieListsEntity>>

    @Query("DELETE FROM movie_lists WHERE id = :id")
    suspend fun deleteMovieListsItem(id: Long)


}