package com.pranshulgg.watchmaster.feature.search.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.search.SearchViewModel
import com.pranshulgg.watchmaster.feature.search.components.SearchItemInfoSheetContent
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItemInfoBottomSheet(
    viewModel: SearchViewModel,
    watchlistViewModel: WatchlistViewModel,
    sheetState: SheetState,
    show: Boolean,
    navController: NavController
) {

    val item = viewModel.uiState.value.selectedItem
    val tvDetailsList = viewModel.uiState.value.selectedSeasonList
    val scope = rememberCoroutineScope()

    if (show && item != null)
        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = {
                viewModel.hideSheet()
            },
            confirmText = "Add to watchlist",
            confirmBtnMaxWidth = true,
            onConfirm = {
                scope.launch {
                    viewModel.addToWatchlist(item, tvDetailsList, watchlistViewModel)
                    SnackbarManager.show("Added to watchlist")
                }
            }
        ) {


            SearchItemInfoSheetContent(
                item = item,
                seasonLoading = viewModel.seasonLoading,
                seasonData = viewModel.seasonData,
                onSelectedSeason = { seasons ->
                    viewModel.updateSelectedSeasonList(seasons)
                },
                watchlistViewModel
            )
        }
}