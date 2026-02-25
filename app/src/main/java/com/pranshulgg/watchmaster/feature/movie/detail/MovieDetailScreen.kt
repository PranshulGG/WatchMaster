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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailsViewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.core.network.TmdbApi
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

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun MovieDetailPage(
    id: Long,
    navController: NavController
) {
//    val viewModel: MovieDetailsViewModel = viewModel(
//        factory = MovieDetailsViewModelFactory(LocalContext.current)
//    )

    val viewModel: MovieDetailsViewModel = hiltViewModel()

    LaunchedEffect(id) {
        viewModel.load(id)
    }


    val loading = viewModel.loading
    var minLoadingDone by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        minLoadingDone = true
    }

    val showLoading = loading || !minLoadingDone // loading to hide nav lag -hack

    val scrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    val scope = rememberCoroutineScope()

    var noteText by remember { mutableStateOf("") }


//    val context = LocalContext.current
//    val repository = remember {
//        val db = WatchMasterDatabase.getInstance(context)
//        WatchlistRepository(
//            db.watchlistDao(),
//            MovieRepository(
//                api = TmdbApi.create(),
//                dao = db.movieBundleDao()
//            )
//        )
//    }
//
//
//    val factory = remember {
//        WatchlistViewModelFactory(repository)
//    }

    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    LaunchedEffect(id) {
        watchlistViewModel.observeItem(id)
    }

    val liveItem by watchlistViewModel.currentItem.collectAsStateWithLifecycle()


    var showRatingDialog by remember { mutableStateOf(false) }


    val existingNoteContent = liveItem?.notes;
    var showNoteDialog by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    note = existingNoteContent ?: ""


    val isMoviePinned = liveItem?.isPinned != true

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            if (!showLoading) {
                MovieDetailFloatingToolBar(
                    scrollBehavior,
                    id,
                    liveItem,
                    startWatching = { watchlistViewModel.start(id) },
                    resetWatching = { watchlistViewModel.reset(id) },
                    finishWatching = { showRatingDialog = true },
                    interruptWatching = { watchlistViewModel.interrupt(id) },
                    onDeleteMovie = {
                        showConfirmationDialog = true
                    },
                    onMoviePin = {
                        watchlistViewModel.setPinned(id, isMoviePinned)
                        SnackbarManager.show(if (isMoviePinned) "Movie pinned" else "Movie unpinned")
                    },
                    isPinned = isMoviePinned,

                    )
            }
        },

        ) { paddingValues ->
        if (showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    .zIndex(10f),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(modifier = Modifier.size(60.dp))
                Box(Modifier.padding(bottom = paddingValues.calculateBottomPadding()))
            }
        }


        viewModel.state?.let {
            LazyColumn(
                modifier = Modifier
                    .nestedScroll(scrollBehavior)
                    .imePadding()
            )
            {


                item {
                    MovieHeroHeader(
                        movie = it,
                        navController,
                        isFinished = liveItem?.status == WatchStatus.FINISHED,
                        userRating = liveItem?.userRating,
                        onUpdateRating = { newRating ->
                            watchlistViewModel.setUserRating(id, newRating)
                            scope.launch {
                                SnackbarManager.show("User rating updated")
                            }

                        }
                    )
                    MediaStatusSection(status = liveItem?.status ?: WatchStatus.WATCHING)
                    Spacer(Modifier.height(16.dp))
                    MediaSectionCard(
                        title = "Overview",
                        titleIcon = R.drawable.overview_24px,
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = it.overview,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    MediaSectionCard(
                        title = "Notes",
                        titleIcon = R.drawable.sticky_note_2_24px,
                        showAction = true,
                        actionOnClick = {
                            showNoteDialog = true
                        },
                        actionText = "Edit note"
                    ) {

                        if (!existingNoteContent.isNullOrBlank()) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    shape = RoundedCornerShape(Radius.Medium),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 64.dp)
                                ) {
                                    Text(
                                        existingNoteContent,
                                        modifier = Modifier.padding(10.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                        }


                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    MediaSectionCard(
                        title = "Cast",
                        titleIcon = R.drawable.groups_2_24px,
                    ) {
                        val director = it.credits.crew.firstOrNull { crew ->
                            crew.job == "Director"
                        }

                        val mainCast = it.credits.cast.take(8)


                        LazyRow {
                            director?.let { director ->
                                item {
                                    Spacer(Modifier.width(8.dp))
                                }
                                item {
                                    CastItem(
                                        character = "Director",
                                        name = director.name,
                                        profilePath = director.profile_path
                                    )

                                }
                                items(mainCast) { castMember ->
                                    CastItem(
                                        character = castMember.character ?: "",
                                        name = castMember.name,
                                        profilePath = castMember.profile_path
                                    )
                                }
                                item {
                                    Spacer(Modifier.width(8.dp))
                                }

                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .windowInsetsBottomHeight(WindowInsets.navigationBars)
                    )
                }
            }

        }

        DialogBasic(
            show = showRatingDialog,
            title = "Rate this movie",
            showDefaultActions = false,
            onDismiss = { showRatingDialog = false },
            content = {
                RateMediaDialogContent(
                    onCancel = { showRatingDialog = false },
                    onConfirm = { rating ->
                        watchlistViewModel.setUserRating(id, rating)
                        watchlistViewModel.finish(id)
                    }
                )
            }
        )
    }

    DialogBasic(
        show = showNoteDialog,
        title = "Add a note",
        showDefaultActions = true,
        onDismiss = {
            showNoteDialog = false
            note = existingNoteContent ?: ""
        },
        onConfirm = {
            watchlistViewModel.setNote(id, note)
        },
        confirmText = "Save",
        content = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Radius.Large),
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("Note...") }
            )
        }
    )

    TextAlertDialog(
        show = showConfirmationDialog,
        title = "Delete movie",
        message = "Are you sure you want to delete this movie? this action cannot be undone",
        confirmText = "Confirm",
        onConfirm = {
            watchlistViewModel.delete(id)
            SnackbarManager.show("Movie deleted ${liveItem?.title}")
            navController.popBackStack()
        },
        onDismiss = {
            showConfirmationDialog = false
        }
    )

}

