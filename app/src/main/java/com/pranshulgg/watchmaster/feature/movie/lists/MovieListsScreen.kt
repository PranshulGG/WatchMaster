package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@Composable
fun MovieListsScreen(navController: NavController) {

    val viewModel: MovieListsViewModel = hiltViewModel()
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    MovieListsScaffold(navController, viewModel, watchlistViewModel)

}