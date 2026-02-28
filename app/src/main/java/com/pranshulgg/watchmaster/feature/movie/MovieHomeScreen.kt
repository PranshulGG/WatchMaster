package com.pranshulgg.watchmaster.feature.movie

import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
import com.pranshulgg.watchmaster.feature.tv.TvHomeState


data class MovieHomeUiState(
    val showConfirmationDialog: Boolean = false,
    val showRatingDialog: Boolean = false,
    val dialogTitle: String = "",
    val dialogMessage: String = "",
    val actionSheetItem: WatchlistItemEntity? = null,
    val updateRating: Boolean = false,
    val originalRating: Float = 0f,
    val selectedItemId: Long? = null
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieHomeScreen(
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val viewModel: WatchlistViewModel = hiltViewModel()

    val items by viewModel.watchlist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val state = remember(items, isLoading) {
        MovieHomeState(items, isLoading)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var uiState by remember { mutableStateOf(MovieHomeUiState()) }

    val deleteMovie: (Long) -> Unit = { id ->
        uiState = uiState.copy(
            dialogTitle = "Delete movie",
            dialogMessage = "Are you sure you want to delete this movie? This action cannot be undone",
            showConfirmationDialog = true,
            selectedItemId = id
        )
    }

    val launchSheet: (WatchlistItemEntity) -> Unit = { item ->
        scope.launch {
            uiState = uiState.copy(actionSheetItem = item)
            sheetState.show()
        }
    }


    // --- Main UI ---
    MovieTabsContent(
        navController = navController,
        scrollBehavior = scrollBehavior,
        scrollBehaviorTopBar = scrollBehaviorTopBar,
        state = state,
        onLongActionMovieRequest = { item ->
            launchSheet(item)
        }
    )


    // --- Confirmation Dialog ---
    TextAlertDialog(
        show = uiState.showConfirmationDialog,
        title = uiState.dialogTitle,
        message = uiState.dialogMessage,
        confirmText = "Confirm",
        onConfirm = {
            uiState.selectedItemId?.let { viewModel.delete(it) }
            SnackbarManager.show("Movie deleted ${uiState.actionSheetItem?.title}")
            uiState = uiState.copy(showConfirmationDialog = false)
        },
        onDismiss = { uiState = uiState.copy(showConfirmationDialog = false) }
    )

    // --- Movie Item Options Bottom Sheet ---
    ActionBottomSheet(
        showActions = false,
        sheetState = sheetState,
        onCancel = { scope.launch { sheetState.hide() } },
        onConfirm = { scope.launch { sheetState.hide() } }
    ) {
        uiState.actionSheetItem?.let { item ->
            WatchlistItemOptionsSheetContent(
                selectedMovieItem = item,
                hideSheet = { scope.launch { sheetState.hide() } },
                onMovieDelete = { id -> id?.let { deleteMovie(it) } },
                onMovieFinish = { id ->
                    uiState = uiState.copy(
                        dialogTitle = "Rate this movie",
                        showRatingDialog = true,
                        selectedItemId = id
                    )
                },
                onUpdateRating = { rating, id ->
                    uiState = uiState.copy(
                        dialogTitle = "Update rating",
                        showRatingDialog = true,
                        updateRating = true,
                        originalRating = rating,
                        selectedItemId = id
                    )
                }
            )
        }
    }


    // --- Rating Dialog ---
    DialogBasic(
        show = uiState.showRatingDialog,
        title = uiState.dialogTitle,
        showDefaultActions = false,
        onDismiss = {
            uiState = uiState.copy(showRatingDialog = false, updateRating = false)
        },
        content = {
            RateMediaDialogContent(
                onCancel = { uiState = uiState.copy(showRatingDialog = false) },
                updateRating = uiState.updateRating,
                originalRating = uiState.originalRating,
                onConfirm = { rating ->
                    uiState.selectedItemId?.let {
                        viewModel.setUserRating(it, rating)
                        viewModel.finish(it)
                    }
                    if (uiState.updateRating) SnackbarManager.show("User rating updated")
                    uiState = uiState.copy(showRatingDialog = false, updateRating = false)
                },
            )
        }
    )
}


