package com.pranshulgg.watchmaster.feature.lists

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@Composable
fun MovieListsScreen(navController: NavController) {

    val viewModel: ListsViewModel = hiltViewModel()
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    ListsScreenScaffold(navController, viewModel, watchlistViewModel)

}