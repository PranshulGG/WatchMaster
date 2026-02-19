package com.pranshulgg.watchmaster.screens.media_detail.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.TvRepository
import com.pranshulgg.watchmaster.models.MovieDetailsViewModel
import com.pranshulgg.watchmaster.network.TmdbApi
import com.pranshulgg.watchmaster.models.TvDetailsViewModel

class TvDetailsViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvDetailsViewModel::class.java)) {

            val db = WatchMasterDatabase.getInstance(context.applicationContext)
            val repo = TvRepository(
                api = TmdbApi.create(),
                dao = db.tvBundleDao()
            )

            @Suppress("UNCHECKED_CAST")
            return TvDetailsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
