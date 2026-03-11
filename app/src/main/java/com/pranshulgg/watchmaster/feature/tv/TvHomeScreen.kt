package com.pranshulgg.watchmaster.feature.tv

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.MovieHomeState
import com.pranshulgg.watchmaster.feature.movie.MovieHomeViewModel
import com.pranshulgg.watchmaster.feature.movie.ui.MovieStatusWatchlistConfirmationDialog
import com.pranshulgg.watchmaster.feature.movie.ui.MovieWatchlistBottomSheet
import com.pranshulgg.watchmaster.feature.movie.ui.MovieWatchlistConfirmationDialog
import com.pranshulgg.watchmaster.feature.movie.ui.MovieWatchlistRatingDialog
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.ui.TvStatusWatchlistConfirmationDialog
import com.pranshulgg.watchmaster.feature.tv.ui.TvWatchlistBottomSheet
import com.pranshulgg.watchmaster.feature.tv.ui.TvWatchlistConfirmationDialog
import com.pranshulgg.watchmaster.feature.tv.ui.TvWatchlistRatingDialog

data class TvHomeUiState(
    val showConfirmationDialog: Boolean = false,
    val showRatingDialog: Boolean = false,
    val actionSheetWatchlistItem: WatchlistItemEntity? = null,
    val actionSheetSeasonItem: SeasonEntity? = null,
    val isUpdateRating: Boolean = false,
    val originalRating: Float = 0f,
    val isSheetOpen: Boolean = false,
    val showStatusConfirmationDialog: Boolean = false,
    val seriesId: Long? = null
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvHomeScreen(
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
) {

    val viewModel: WatchlistViewModel = hiltViewModel()

    val items by viewModel.watchlist.collectAsState()
    val seasonItems by viewModel.seasons.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val tvHomeViewModel: TvHomeViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val currentSeasonItem = tvHomeViewModel.uiState.value.actionSheetSeasonItem


    val state = remember(items, isLoading, seasonItems) {
        filterTvItems(items, isLoading, seasonItems)
    }


    TvTabsContent(
        navController,
        state,
        scrollBehavior,
        scrollBehaviorTopBar,
        onLongActionTvSeasonRequest = { item, watchlistItem ->
            tvHomeViewModel.showBottomSheet(item, watchlistItem)
        },
    )


    TvWatchlistBottomSheet(
        show = tvHomeViewModel.uiState.value.isSheetOpen,
        sheetState = sheetState,
        watchlistItem = tvHomeViewModel.uiState.value.actionSheetWatchlistItem,
        seasonItem = currentSeasonItem,
        onDismiss = tvHomeViewModel::hideBottomSheet,
        viewModel = viewModel,
        tvHomeViewModel = tvHomeViewModel

    )

    TvWatchlistConfirmationDialog(
        watchlistViewModel = viewModel,
        tvHomeViewModel = tvHomeViewModel,
        season = currentSeasonItem
    )

    TvWatchlistRatingDialog(
        watchlistViewModel = viewModel,
        tvHomeViewModel = tvHomeViewModel,
        season = currentSeasonItem
    )

    TvStatusWatchlistConfirmationDialog(
        watchlistViewModel = viewModel,
        tvHomeViewModel = tvHomeViewModel,
        season = currentSeasonItem
    )

}