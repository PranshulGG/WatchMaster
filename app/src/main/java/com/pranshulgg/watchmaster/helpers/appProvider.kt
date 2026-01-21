package com.pranshulgg.watchmaster.helpers

import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository

fun provideWatchlistRepository(context: Context): WatchlistRepository {
    val db = WatchMasterDatabase.getInstance(context.applicationContext)
    return WatchlistRepository(db.watchlistDao())
}
