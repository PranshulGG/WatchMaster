package com.pranshulgg.watchmaster.feature.tv.detail

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.ui.FloatingToolbarMediaActionsParams
import com.pranshulgg.watchmaster.feature.shared.media.ui.MediaActionsFloatingToolbar
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsConfirmationDialog
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsNoteDialog
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsRatingDialog
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvWatchProviderSheet

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TvDetailsScaffold(
    id: Long,
    seasonNumber: Int,
    seasonId: Long,
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
    val episodesFlow = remember(seasonId) { viewModel.seasonEpisodes(seasonId) }
    val episodes by episodesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    val isSeriesPinned = watchlistItem?.isPinned == true

    if (loading || season == null) {
        LoadingScreenPlaceholder()
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            MediaActionsFloatingToolbar(
                scrollBehavior,
                season.status,
                actions = FloatingToolbarMediaActionsParams(
                    startWatching = { watchlistViewModel.startSeason(season.seasonId) },
                    resetWatching = { watchlistViewModel.resetSeason(season.seasonId) },
                    finishWatching = { viewModel.showRatingDialog() },
                    interruptWatching = { watchlistViewModel.interruptSeason(season.seasonId) },
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
        },

        )
    { _ ->
        viewModel.state?.let { tvItem ->
            TvDetailsContent(
                tvItem,
                navController,
                scrollBehavior,
                seasons,
                seasonNumber,
                season,
                viewModel,
                watchlistViewModel,
                episodes
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
    TvWatchProviderSheet(
        sheetState,
        onDismiss = { viewModel.hideWatchProviderSheet() },
        viewModel
    )
}