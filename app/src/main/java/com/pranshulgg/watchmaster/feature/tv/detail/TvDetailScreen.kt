package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.components.ErrorContainer
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
    var showError by remember { mutableStateOf(false) }


    TvDetailEffects(id, seasonNumber, viewModel, seasonId, onError = { showError = true })

    val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    if (showError) {
        ErrorContainer(onRetry = {
            showError = false
            viewModel.load(id, onError = { showError = true })
        }, errorDescription = "Failed to load series details. Please try again")
    }


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

