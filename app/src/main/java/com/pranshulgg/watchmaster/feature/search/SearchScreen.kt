package com.pranshulgg.watchmaster.feature.search

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import com.pranshulgg.watchmaster.feature.search.ui.SearchItemInfoBottomSheet
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel


data class SearchUiState(
    val selectedItem: SearchItem? = null,
    val selectedSeasonList: List<TvSeasonDto> = emptyList(),
    val isSheetOpen: Boolean = false
)

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SearchScreen(
    navController: NavController,
    searchType: SearchType
) {

    val viewModel: SearchViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    val scrollBehaviorToolbar =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    SearchScreenScaffold(
        viewModel,
        navController,
        scrollBehavior,
        scrollBehaviorToolbar,
        searchType,
        watchlistViewModel
    )


    SearchItemInfoBottomSheet(
        viewModel,
        watchlistViewModel,
        sheetState,
        viewModel.uiState.value.isSheetOpen,
        navController
    )

}



