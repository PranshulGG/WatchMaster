package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.TvBundleEntity

@Dao
interface TvBundleDao {

    @Query("SELECT * FROM tv_bundle WHERE id = :id")
    suspend fun getById(id: Long): TvBundleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TvBundleEntity)

    @Query("DELETE FROM tv_bundle")
    suspend fun clearAll()

    @Query("DELETE FROM tv_bundle WHERE id = :id")
    suspend fun deleteById(id: Long)
}
