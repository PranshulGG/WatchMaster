package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.data.local.entity.TrendingEntity


@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrending(trending: List<TrendingEntity>)

    @Query("SELECT * FROM trending_data WHERE trendingType = :trendingType")
    suspend fun getTrending(trendingType: String): List<TrendingEntity>

    @Query("DELETE FROM trending_data WHERE trendingType = :trendingType")
    suspend fun clearTrending(trendingType: String)


}