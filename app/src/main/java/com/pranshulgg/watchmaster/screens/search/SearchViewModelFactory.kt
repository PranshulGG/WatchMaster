package com.pranshulgg.watchmaster.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pranshulgg.watchmaster.network.TmdbApi

class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = TmdbApi.create()
        val repo = SearchRepository(api)
        return SearchViewModel(repo) as T
    }
}
