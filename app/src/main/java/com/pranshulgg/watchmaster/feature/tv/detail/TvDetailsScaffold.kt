package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvDetailFloatingToolbar

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvDetailsScaffold(
    id: Long,
    seasonNumber: Int,
    scrollBehavior: FloatingToolbarScrollBehavior,
    uiState: TvDetailsUiState,
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    navController: NavController
) {

    val loading = viewModel.loading
//    val showLoading = loading
    val seasonsData by watchlistViewModel.seasonsForShow(id).collectAsState(initial = emptyList())
    val season = seasonsData.find { it.seasonNumber == seasonNumber }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            if (!loading && season != null) {
                TvDetailFloatingToolbar(
                    scrollBehavior,
                    id,
                    season,
                    startWatching = {
                        watchlistViewModel.markSeasonStatus(
                            id,
                            WatchStatus.WATCHING
                        )
                    },
                    resetWatching = {
                        watchlistViewModel.markSeasonStatus(
                            id,
                            WatchStatus.WANT_TO_WATCH
                        )
                    },
                    finishWatching = {
//                        uiState = uiState.copy(showRatingDialog = true)
                        uiState.showRatingDialog
                    },
                    interruptWatching = {
                        watchlistViewModel.markSeasonStatus(
                            id,
                            WatchStatus.INTERRUPTED
                        )
                    },
                    onDeleteSeason = {
//                        uiState = uiState.copy(showConfirmationDialog = true)
                        uiState.showConfirmationDialog
                    },
                    onPin = {
                        liveItem?.let { watchlistViewModel.setPinned(it.id, isTvPinned) }
                        SnackbarManager.show(if (isTvPinned) "Series pinned" else "Series unpinned")
                    },
                    isPinned = isTvPinned,
                    isTv = isTv
                )
            }
        }
    )
    { paddingValues ->
        if (loading) {
            LoadingScreenPlaceholder()
            Box(modifier = Modifier.padding(paddingValues)) // just use em
        }
        viewModel.state?.let { tvItem ->
            // --- Main UI ---
            TvDetailsContent(
                tvItem,
                navController,
                scrollBehavior,
                seasonsData,
                seasonNumber,
                season,
                {
                    uiState = uiState.copy(
                        note = season?.seasonNotes ?: "",
                        showNoteDialog = true
                    )
                }
            )
        }
    }
}