package com.pranshulgg.watchmaster.feature.tv

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.feature.movie.MovieHomeState
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvHomeScreen(
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
) {

    val viewModel: WatchlistViewModel = hiltViewModel()

    val items by viewModel.watchlist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val state = remember(items, isLoading) {
        TvHomeState(items, isLoading)
    }


    TvTabsContent(navController, state, scrollBehavior, scrollBehaviorTopBar, viewModel)

}