package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity

@Dao
interface MovieBundleDao {

    @Query("SELECT * FROM movie_bundle WHERE id = :id")
    suspend fun getById(id: Long): MovieBundleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MovieBundleEntity)

    @Query("DELETE FROM movie_bundle")
    suspend fun clearAll()

    @Query("DELETE FROM movie_bundle WHERE id = :id")
    suspend fun deleteById(id: Long)
}
