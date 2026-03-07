package com.pranshulgg.watchmaster.feature.movie

import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.components.media.RateMediaDialogContent
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.movie.components.WatchlistItemOptionsSheetContent
import com.pranshulgg.watchmaster.feature.movie.ui.MovieWatchlistBottomSheet
import com.pranshulgg.watchmaster.feature.movie.ui.MovieWatchlistConfirmationDialog
import com.pranshulgg.watchmaster.feature.movie.ui.MovieWatchlistRatingDialog
import com.pranshulgg.watchmaster.feature.tv.TvHomeState


data class MovieHomeUiState(
    val showConfirmationDialog: Boolean = false,
    val showRatingDialog: Boolean = false,
    val actionSheetItem: WatchlistItemEntity? = null,
    val isUpdateRating: Boolean = false,
    val originalRating: Float = 0f,
    val isSheetOpen: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieHomeScreen(
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
) {

    val viewModel: WatchlistViewModel = hiltViewModel()
    val items by viewModel.watchlist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val state = remember(items, isLoading) {
        MovieHomeState(items, isLoading)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val uiStateViewModel: MovieHomeViewModel = hiltViewModel()

    MovieTabsContent(
        navController = navController,
        scrollBehavior = scrollBehavior,
        scrollBehaviorTopBar = scrollBehaviorTopBar,
        state = state,
        onLongActionMovieRequest = { item ->
            uiStateViewModel.showBottomSheet(item)
        }
    )

//
//    TextAlertDialog(
//        show = uiState.showConfirmationDialog,
//        title = uiState.dialogTitle,
//        message = uiState.dialogMessage,
//        confirmText = "Confirm",
//        onConfirm = {
//            uiState.selectedItemId?.let { viewModel.delete(it) }
//            SnackbarManager.show("Movie deleted ${uiState.actionSheetItem?.title}")
//            uiState = uiState.copy(showConfirmationDialog = false)
//        },
//        onDismiss = { uiState = uiState.copy(showConfirmationDialog = false) }
//    )

    MovieWatchlistBottomSheet(
        show = uiStateViewModel.uiState.value.isSheetOpen,
        sheetState = sheetState,
        watchlistItem = uiStateViewModel.uiState.value.actionSheetItem,
        onDismiss = uiStateViewModel::hideBottomSheet,
        viewModel,
        uiStateViewModel
    )

    MovieWatchlistConfirmationDialog(
        watchlistViewModel = viewModel,
        movieHomeViewModel = uiStateViewModel,
        navController = navController,
        watchlistItem = uiStateViewModel.uiState.value.actionSheetItem
    )

    MovieWatchlistRatingDialog(
        watchlistViewModel = viewModel,
        movieHomeViewModel = uiStateViewModel,
        watchlistItem = uiStateViewModel.uiState.value.actionSheetItem
    )

//    ActionBottomSheet(
//        showActions = false,
//        sheetState = sheetState,
//        onCancel = { scope.launch { sheetState.hide() } },
//        onConfirm = { scope.launch { sheetState.hide() } }
//    ) {
//        uiState.actionSheetItem?.let { item ->
//            WatchlistItemOptionsSheetContent(
//                selectedMovieItem = item,
//                hideSheet = { scope.launch { sheetState.hide() } },
//                onMovieDelete = { id -> id?.let { deleteMovie(it) } },
//                onMovieFinish = { id ->
//                    uiState = uiState.copy(
//                        dialogTitle = "Rate this movie",
//                        showRatingDialog = true,
//                        selectedItemId = id
//                    )
//                },
//                onUpdateRating = { rating, id ->
//                    uiState = uiState.copy(
//                        dialogTitle = "Update rating",
//                        showRatingDialog = true,
//                        updateRating = true,
//                        originalRating = rating,
//                        selectedItemId = id
//                    )
//                }
//            )
//        }
//    }


//    DialogBasic(
//        show = uiState.showRatingDialog,
//        title = uiState.dialogTitle,
//        showDefaultActions = false,
//        onDismiss = {
//            uiState = uiState.copy(showRatingDialog = false, updateRating = false)
//        },
//        content = {
//            RateMediaDialogContent(
//                onCancel = { uiState = uiState.copy(showRatingDialog = false) },
//                updateRating = uiState.updateRating,
//                originalRating = uiState.originalRating,
//                onConfirm = { rating ->
//                    uiState.selectedItemId?.let {
//                        viewModel.setUserRating(it, rating)
//                        viewModel.finish(it)
//                    }
//                    if (uiState.updateRating) SnackbarManager.show("User rating updated")
//                    uiState = uiState.copy(showRatingDialog = false, updateRating = false)
//                },
//            )
//        }
//    )

}


