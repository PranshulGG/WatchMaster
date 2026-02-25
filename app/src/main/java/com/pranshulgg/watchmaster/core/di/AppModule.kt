package com.pranshulgg.watchmaster.core.di

import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import android.content.Context
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.feature.search.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WatchMasterDatabase =
        WatchMasterDatabase.getInstance(context)

    @Provides
    fun provideWatchlistDao(db: WatchMasterDatabase) =
        db.watchlistDao()

    @Provides
    fun provideMovieBundleDao(db: WatchMasterDatabase) =
        db.movieBundleDao()

    @Provides
    @Singleton
    fun provideTmdbApi(): TmdbApi =
        TmdbApi.create()

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: TmdbApi,
        movieBundleDao: MovieBundleDao
    ): MovieRepository =
        MovieRepository(api, movieBundleDao)

    @Provides
    @Singleton
    fun provideWatchlistRepository(
        watchlistDao: WatchlistDao,
        movieRepository: MovieRepository
    ): WatchlistRepository =
        WatchlistRepository(watchlistDao, movieRepository)

    @Provides
    @Singleton
    fun provideSearchRepository(
        api: TmdbApi
    ): SearchRepository =
        SearchRepository(api)
}