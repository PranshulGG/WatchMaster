package com.pranshulgg.watchmaster.screens.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.network.TmdbApi
import com.pranshulgg.watchmaster.data.local.dao.WatchlistDao

class SearchViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = TmdbApi.create()
        val searchRepo = SearchRepository(api)

        val db = WatchMasterDatabase.getInstance(context)
        val watchlistRepo = WatchlistRepository(db.watchlistDao())

        return SearchViewModel(
            searchRepo,
            watchlistRepo
        ) as T
    }
}
