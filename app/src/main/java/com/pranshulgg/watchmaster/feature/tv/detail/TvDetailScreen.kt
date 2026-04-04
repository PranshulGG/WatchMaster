package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.data.CountryWatchProviders
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailEffects

data class TvDetailsUiState(
    val showNoteDialog: Boolean = false,
    val note: String = "",
    val showRatingDialog: Boolean = false,
    val showConfirmationDialog: Boolean = false,
    val isWatchProviderSheetOpen: Boolean = false,
    val currentWatchProviders: CountryWatchProviders? = null
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvDetailsScreen(id: Long, seasonNumber: Int, navController: NavController, seasonId: Long) {

    val viewModel: TvDetailsViewModel = hiltViewModel()
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    TvDetailEffects(id, seasonNumber, viewModel, seasonId)

    val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)


    TvDetailsScaffold(
        id = id,
        seasonNumber = seasonNumber,
        scrollBehavior = scrollBehavior,
        viewModel = viewModel,
        watchlistViewModel = watchlistViewModel,
        navController = navController,
        seasonId = seasonId
    )

}

