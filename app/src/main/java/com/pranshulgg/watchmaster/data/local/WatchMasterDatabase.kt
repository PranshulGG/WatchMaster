package com.pranshulgg.watchmaster.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.converters.GenreIdsConverter
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pranshulgg.watchmaster.data.local.converters.InstantConverters
import com.pranshulgg.watchmaster.data.local.converters.LongListConverter
import com.pranshulgg.watchmaster.data.local.converters.SeasonNameConverter
import com.pranshulgg.watchmaster.data.local.converters.WatchStatusConverter
import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.dao.MovieListsDao
import com.pranshulgg.watchmaster.data.local.dao.SeasonDao
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.local.dao.TvEpisodeDao
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.TvBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity

@Database(
    entities = [WatchlistItemEntity::class, MovieBundleEntity::class, TvBundleEntity::class, SeasonEntity::class, TvEpisodeEntity::class, MovieListsEntity::class],
    version = 27
)
@TypeConverters(
    GenreIdsConverter::class,
    InstantConverters::class,
    WatchStatusConverter::class,
    SeasonNameConverter::class,
    LongListConverter::class
)
abstract class WatchMasterDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao
    abstract fun movieBundleDao(): MovieBundleDao

    abstract fun tvBundleDao(): TvBundleDao

    abstract fun seasonDao(): SeasonDao

    abstract fun tvEpisodeDao(): TvEpisodeDao

    abstract fun movieListsDao(): MovieListsDao

    companion object {
        @Volatile
        private var INSTANCE: WatchMasterDatabase? = null

        fun getInstance(context: Context): WatchMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WatchMasterDatabase::class.java,
                    "watchmaster.db"
                ).addMigrations(MIGRATION_25_26, MIGRATION_26_27).build().also { INSTANCE = it }
            }
        }
    }
}

val MIGRATION_25_26 = object : Migration(25, 26) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS movie_lists (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                description TEXT,
                icon INTEGER,
                movieIds TEXT NOT NULL DEFAULT ''
            )
        """.trimIndent()
        )
    }
}


val MIGRATION_26_27 = object : Migration(26, 27) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE movie_lists_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                icon INTEGER,
                movieIds TEXT NOT NULL
            )
        """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO movie_lists_new (id, name, description, icon, movieIds)
            SELECT id, name, 
                   COALESCE(description, ''), 
                   icon, 
                   movieIds
            FROM movie_lists
        """.trimIndent()
        )

        db.execSQL("DROP TABLE movie_lists")
        db.execSQL("ALTER TABLE movie_lists_new RENAME TO movie_lists")
    }
}
