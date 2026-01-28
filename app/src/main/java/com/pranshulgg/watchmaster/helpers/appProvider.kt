package com.pranshulgg.watchmaster.helpers

import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.network.TmdbApi

fun provideWatchlistRepository(context: Context): WatchlistRepository {
    val db = WatchMasterDatabase.getInstance(context.applicationContext)

    val movieRepo = MovieRepository(
        api = TmdbApi.create(),
        dao = db.movieBundleDao()
    )

    return WatchlistRepository(
        dao = db.watchlistDao(),
        movieRepository = movieRepo
    )
}
