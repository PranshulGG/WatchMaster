package com.pranshulgg.watchmaster.feature.movie.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.data.CountryWatchProviders
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel


data class MovieDetailsUiState(
    val showNoteDialog: Boolean = false,
    val note: String = "",
    val showRatingDialog: Boolean = false,
    val showConfirmationDialog: Boolean = false,
    val isWatchProviderSheetOpen: Boolean = false,
    val currentWatchProviders: CountryWatchProviders? = null
)

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun MovieDetailPage(
    id: Long,
    navController: NavController
) {

    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    LaunchedEffect(id) {
        viewModel.load(id, onBack = { navController.popBackStack() })
    }


    MovieDetailsScaffold(
        id,
        scrollBehavior,
        viewModel,
        watchlistViewModel,
        navController
    )

}

