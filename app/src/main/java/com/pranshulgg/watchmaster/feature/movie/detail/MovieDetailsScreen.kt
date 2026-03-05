package com.pranshulgg.watchmaster.feature.movie.detail

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.components.media.CastItem
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.core.ui.components.media.RateMediaDialogContent
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieDetailFloatingToolBar
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieHeroHeader
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class MovieDetailsUiState(
    val showNoteDialog: Boolean = false,
    val note: String = "",
    val showRatingDialog: Boolean = false,
    val showConfirmationDialog: Boolean = false
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
//
    LaunchedEffect(id) {
        viewModel.load(id)
    }


    val loading = viewModel.loading
    var minLoadingDone by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        minLoadingDone = true
    }

    val showLoading = loading || !minLoadingDone

    val scrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    val scope = rememberCoroutineScope()

    var noteText by remember { mutableStateOf("") }

    val watchlistViewModel: WatchlistViewModel = hiltViewModel()


    val liveItem by watchlistViewModel.item(id).collectAsStateWithLifecycle()


    var showRatingDialog by remember { mutableStateOf(false) }


    val existingNoteContent = liveItem?.notes;
    var showNoteDialog by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    note = existingNoteContent ?: ""


//    val isMoviePinned = liveItem?.isPinned != true

    MovieDetailsScaffold(
        id,
        scrollBehavior,
        viewModel,
        watchlistViewModel,
        navController
    )


//        DialogBasic(
//            show = showRatingDialog,
//            title = "Rate this movie",
//            showDefaultActions = false,
//            onDismiss = { showRatingDialog = false },
//            content = {
//                RateMediaDialogContent(
//                    onCancel = { showRatingDialog = false },
//                    onConfirm = { rating ->
//                        watchlistViewModel.setUserRating(id, rating)
//                        watchlistViewModel.finish(id)
//                    }
//                )
//            }
//        )
//    }
//
//    DialogBasic(
//        show = showNoteDialog,
//        title = "Add a note",
//        showDefaultActions = true,
//        onDismiss = {
//            showNoteDialog = false
//            note = existingNoteContent ?: ""
//        },
//        onConfirm = {
//            watchlistViewModel.setNote(id, note)
//        },
//        confirmText = "Save",
//        content = {
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(Radius.Large),
//                value = note,
//                onValueChange = { note = it },
//                placeholder = { Text("Note...") }
//            )
//        }
//    )
//
//    TextAlertDialog(
//        show = showConfirmationDialog,
//        title = "Delete movie",
//        message = "Are you sure you want to delete this movie? this action cannot be undone",
//        confirmText = "Confirm",
//        onConfirm = {
//            watchlistViewModel.delete(id)
//            SnackbarManager.show("Movie deleted ${liveItem?.title}")
//            navController.popBackStack()
//        },
//        onDismiss = {
//            showConfirmationDialog = false
//        }
//    )

}

