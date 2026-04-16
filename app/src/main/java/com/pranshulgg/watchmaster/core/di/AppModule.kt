package com.pranshulgg.watchmaster.core.di

import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao
import android.content.Context
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.data.local.dao.CustomListsDao
import com.pranshulgg.watchmaster.data.local.dao.SeasonDao
import com.pranshulgg.watchmaster.data.local.dao.TrendingDao
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.local.dao.TvEpisodeDao
import com.pranshulgg.watchmaster.data.repository.CustomListsRepository
import com.pranshulgg.watchmaster.data.repository.PersonRepository
import com.pranshulgg.watchmaster.data.repository.SearchRepository
import com.pranshulgg.watchmaster.data.repository.TrendingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import com.pranshulgg.watchmaster.data.repository.TvRepository

@Module
@InstallIn(SingletonComponent::class)
@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
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
    fun provideTvEpisodeDao(db: WatchMasterDatabase) =
        db.tvEpisodeDao()

    @Provides
    fun provideMovieListsDao(db: WatchMasterDatabase) =
        db.movieListsDao()

    @Provides
    fun provideTrendingDao(db: WatchMasterDatabase) =
        db.trendingDao()


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
        tvRepository: TvRepository
    ): WatchlistRepository =
        WatchlistRepository(watchlistDao, seasonDao, movieRepository, tvRepository)

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
        tvBundleDao: TvBundleDao,
        tvEpisodeDao: TvEpisodeDao,
        seasonDao: SeasonDao
    ): TvRepository = TvRepository(api, tvBundleDao, tvEpisodeDao, seasonDao)

    @Provides
    @Singleton
    fun provideCustomListsRepository(
        movieListsDao: CustomListsDao
    ): CustomListsRepository = CustomListsRepository(movieListsDao)

    @Provides
    @Singleton
    fun providePersonRepository(
        api: TmdbApi
    ): PersonRepository = PersonRepository(api)

    @Provides
    @Singleton
    fun provideTrendingRepository(
        api: TmdbApi,
        dao: TrendingDao
    ): TrendingRepository = TrendingRepository(api, dao)
}