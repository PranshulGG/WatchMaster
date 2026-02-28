package com.pranshulgg.watchmaster.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.converters.GenreIdsConverter
import androidx.room.Room
import com.pranshulgg.watchmaster.data.local.converters.InstantConverters
import com.pranshulgg.watchmaster.data.local.converters.SeasonNameConverter
import com.pranshulgg.watchmaster.data.local.converters.WatchStatusConverter
import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.dao.SeasonDao
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistSeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.TvBundleEntity

@Database(
    entities = [WatchlistItemEntity::class, MovieBundleEntity::class, TvBundleEntity::class, WatchlistSeasonEntity::class],
    version = 18
)
@TypeConverters(
    GenreIdsConverter::class,
    InstantConverters::class,
    WatchStatusConverter::class,
    SeasonNameConverter::class
)
abstract class WatchMasterDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao
    abstract fun movieBundleDao(): MovieBundleDao

    abstract fun tvBundleDao(): TvBundleDao

    abstract fun seasonDao(): SeasonDao

    companion object {
        @Volatile
        private var INSTANCE: WatchMasterDatabase? = null

        fun getInstance(context: Context): WatchMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WatchMasterDatabase::class.java,
                    "watchmaster.db"
                ).fallbackToDestructiveMigration(true).build().also { INSTANCE = it }
            }
        }
    }
}
