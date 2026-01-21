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
import com.pranshulgg.watchmaster.data.local.converters.WatchStatusConverter

@Database(
    entities = [WatchlistItemEntity::class],
    version = 5
)
@TypeConverters(
    GenreIdsConverter::class,
    InstantConverters::class,
    WatchStatusConverter::class
)
abstract class WatchMasterDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao

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
