package com.pranshulgg.watchmaster.core.di

import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import android.content.Context
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.data.local.dao.SeasonDao
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import com.pranshulgg.watchmaster.data.repository.TvRepository

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
    fun provideTvBundleDao(db: WatchMasterDatabase) =
        db.tvBundleDao()

    @Provides
    fun provideSeasonDao(db: WatchMasterDatabase) =
        db.seasonDao()

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
        seasonDao: SeasonDao,
        movieRepository: MovieRepository,
    ): WatchlistRepository =
        WatchlistRepository(watchlistDao, seasonDao, movieRepository)

    @Provides
    @Singleton
    fun provideSearchRepository(
        api: TmdbApi
    ): SearchRepository =
        SearchRepository(api)

    @Provides
    @Singleton
    fun providerTvRepository(
        api: TmdbApi,
        tvBundleDao: TvBundleDao
    ): TvRepository = TvRepository(api, tvBundleDao)
}