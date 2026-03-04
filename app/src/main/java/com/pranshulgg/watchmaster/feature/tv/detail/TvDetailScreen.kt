package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.components.media.CastItem
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.core.ui.components.media.RateMediaDialogContent
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailsViewModel
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieDetailFloatingToolBar
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieHeroHeader
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.CastTvSection
import com.pranshulgg.watchmaster.feature.shared.media.components.NotesSection
import com.pranshulgg.watchmaster.feature.shared.media.components.OverviewSection
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvDetailFloatingToolbar
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvHeroHeader
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailEffects
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TvDetailsUiState(
    val minLoadingDone: Boolean = false,
    val showNoteDialog: Boolean = false,
    val note: String = "",
    val showRatingDialog: Boolean = false,
    val showConfirmationDialog: Boolean = false
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvDetailsScreen(id: Long, seasonNumber: Int, navController: NavController) {
    val viewModel: TvDetailsViewModel = hiltViewModel()
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
//    var uiState by remember { mutableStateOf(TvDetailsUiState()) }
    val uiState by viewModel.uiState
//    LaunchedEffect(id) {
//        viewModel.load(id, seasonNumber)
//    }
//    LaunchedEffect(id) {
//        watchlistViewModel.observeItem(id)
//    }
//    LaunchedEffect(Unit) {
//        delay(1000)
//        uiState = uiState.copy(minLoadingDone = true)
//    }

    TvDetailEffects(id, seasonNumber, viewModel, watchlistViewModel)
//    val loading = viewModel.loading
    val scrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)
    val liveItem by watchlistViewModel.currentItem.collectAsStateWithLifecycle()
    val seasonsData by watchlistViewModel
        .seasonsForShow(id)
        .collectAsState(initial = emptyList())
//    val showLoading = loading || !uiState.minLoadingDone
    val season = seasonsData.find { it.seasonNumber == seasonNumber }
    val watchlistItem = liveItem
    val isTvPinned = watchlistItem?.isPinned == true
    val isTv = watchlistItem?.mediaType == "tv"


    TvDetailsScaffold(
        id = id,
        seasonNumber = seasonNumber,
        scrollBehavior = scrollBehavior,
        uiState = uiState,
        viewModel = viewModel,
        watchlistViewModel = watchlistViewModel,
        navController = navController
    )

    // --- Notes dialog ---
    DialogBasic(
        show = uiState.showNoteDialog,
        title = "Add a note",
        showDefaultActions = true,
        onDismiss = {
//            uiState = uiState.copy(showNoteDialog = false)
//            uiState = uiState.copy(note = season?.seasonNotes ?: "")
        },
        onConfirm = {
            watchlistViewModel.setSeasonNote(id, uiState.note)
        },
        confirmText = "Save",
        content = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Radius.Large),
                value = uiState.note,
                onValueChange = { uiState = uiState.copy(note = it) },
                placeholder = { Text("Note...") }
            )
        }
    )


    // --- Rating Dialog ---
    DialogBasic(
        show = uiState.showRatingDialog,
        title = "Rate this season",
        showDefaultActions = false,
//        onDismiss = { uiState = uiState.copy(showRatingDialog = false) },
        content = {
            RateMediaDialogContent(
//                onCancel = { uiState = uiState.copy(showRatingDialog = false) },
                onConfirm = { rating ->
                    watchlistViewModel.setSeasonUserRating(id, rating)
                    watchlistViewModel.markSeasonStatus(id, WatchStatus.FINISHED)
                }
            )
        }
    )

    // --- Confirmation Dialog ---
    TextAlertDialog(
        show = uiState.showConfirmationDialog,
        title = "Delete season",
        message = "Are you sure you want to delete this season? this action cannot be undone",
        confirmText = "Confirm",
        onConfirm = {
            if (seasonsData.size == 1) {
                watchlistViewModel.delete(id)
            } else {
                watchlistViewModel.deleteSeason(id)
            }
            SnackbarManager.show("Season deleted ${season?.name}")
            navController.popBackStack()
        },
        onDismiss = {
//            uiState = uiState.copy(showConfirmationDialog = false)
        }
    )
}

