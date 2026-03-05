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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.ui.MediaActionsFloatingToolbar
import com.pranshulgg.watchmaster.feature.shared.media.ui.FloatingToolbarMediaActionsParams
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvDetailFloatingToolbar
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsConfirmationDialog
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsNoteDialog
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsRatingDialog

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvDetailsScaffold(
    id: Long,
    seasonNumber: Int,
    scrollBehavior: FloatingToolbarScrollBehavior,
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    navController: NavController
) {

    val loading = viewModel.loading
    val seasons by watchlistViewModel.seasonsForShow(id).collectAsState(initial = emptyList())
    val season = seasons.find { it.seasonNumber == seasonNumber }
    val watchlistFlow = remember(id) { watchlistViewModel.item(id) }
    val watchlistItem by watchlistFlow.collectAsStateWithLifecycle()

    val isSeriesPinned = watchlistItem?.isPinned == true

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            if (!loading && season != null) {
                MediaActionsFloatingToolbar(
                    scrollBehavior,
                    season.status,
                    actions = FloatingToolbarMediaActionsParams(
                        startWatching = { watchlistViewModel.startSeason(id) },
                        resetWatching = { watchlistViewModel.resetSeason(id) },
                        finishWatching = { viewModel.showRatingDialog() },
                        interruptWatching = { watchlistViewModel.interruptSeason(id) },
                        delete = { viewModel.showConfirmationDialog() },
                        togglePin = {
                            watchlistItem?.let {
                                watchlistViewModel.setPinned(
                                    season.showId,
                                    !it.isPinned
                                )
                            }
                        },
                    ),
                    isPinned = isSeriesPinned,
                    isTv = true
                )
            }
        }
    )
    { paddingValues ->
        if (loading) {
            LoadingScreenPlaceholder()
            Box(modifier = Modifier.padding(paddingValues))
        }
        viewModel.state?.let { tvItem ->
            TvDetailsContent(
                tvItem,
                navController,
                scrollBehavior,
                seasons,
                seasonNumber,
                season,
                viewModel,
                watchlistViewModel
            )
        }
    }

    TvDetailsNoteDialog(viewModel, watchlistViewModel, season)
    TvDetailsRatingDialog(viewModel, watchlistViewModel, season)
    TvDetailsConfirmationDialog(
        viewModel,
        watchlistViewModel,
        season,
        seasons.size,
        navController
    )

}