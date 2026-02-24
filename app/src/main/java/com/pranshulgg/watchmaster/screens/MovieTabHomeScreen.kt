package com.pranshulgg.watchmaster.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.utils.Symbol
import kotlinx.coroutines.launch
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModelFactory
import com.pranshulgg.watchmaster.network.TmdbApi
import com.pranshulgg.watchmaster.screens.movieTabs.finished.FinishedMovies
import com.pranshulgg.watchmaster.screens.movieTabs.ui.WatchlistItemOptionsSheetContent
import com.pranshulgg.watchmaster.screens.movieTabs.watching.WatchingMovies
import com.pranshulgg.watchmaster.screens.movieTabs.watchlist.WatchlistMovies
import com.pranshulgg.watchmaster.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.ui.components.DialogBasic
import com.pranshulgg.watchmaster.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.ui.components.RateMovieDialogContent
import com.pranshulgg.watchmaster.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.ui.snackbar.SnackbarManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieTabHomeScreen(
    navController: NavController,
    motionScheme: MotionScheme,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
) {

    val tabs = listOf("Watchlist", "Watching", "Finished")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val db = WatchMasterDatabase.getInstance(context)
    val repository = WatchlistRepository(
        db.watchlistDao(), MovieRepository(
            api = TmdbApi.create(),
            dao = db.movieBundleDao()
        )
    )

    val viewModel: WatchlistViewModel = viewModel(
        factory = WatchlistViewModelFactory(repository)
    )

    val items by viewModel.watchlist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showRatingDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var longPressedItemId by remember { mutableStateOf<Long?>(null) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }
    var actionSheetItem by remember { mutableStateOf<WatchlistItemEntity?>(null) }


    val deleteMovieFun: (Long) -> Unit = { id ->
        longPressedItemId = id
        dialogTitle = "Delete movie";
        dialogMessage = "Are you sure you want to delete this movie? this action cannot be undone"
        showConfirmationDialog = true
    }

    val launchSheet: (WatchlistItemEntity) -> Unit = { item ->
        scope.launch {
            actionSheetItem = item
            sheetState.show()
        }
    }

    var updateRating by remember { mutableStateOf(false) }
    var originalRating by remember { mutableFloatStateOf(0f) }





    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            PrimaryTabRow(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                selectedTabIndex = pagerState.currentPage,
            ) {
                tabs.forEachIndexed { index, title ->

                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }

                        },
                        text = { Text(title) }
                    )
                }
            }
        },
//        contentWindowInsets = WindowInsets(bottom = 0, top = 0),
    ) { innerPadding ->


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->


            when (page) {
                0 ->
                    WatchlistMovies(
                        isLoading,
                        items = items.filter {
                            it.status == WatchStatus.WANT_TO_WATCH && it.mediaType != "tv"
                        },
                        scrollBehavior,
                        scrollBehaviorTopBar,
                        navController,
                        onLongActionMovieRequest = { item ->
                            launchSheet(item)
                        }
                    )


                1 -> WatchingMovies(
                    isLoading,
                    items = items.filter {
                        (it.status == WatchStatus.WATCHING || it.status == WatchStatus.INTERRUPTED) && it.mediaType != "tv"

                    },
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController,
                    onLongActionMovieRequest = { item ->
                        launchSheet(item)
                    }

                )

                2 -> FinishedMovies(
                    isLoading,
                    items = items.filter {
                        it.status == WatchStatus.FINISHED && it.mediaType != "tv"
                    },
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController,
                    onLongActionMovieRequest = { item ->
                        launchSheet(item)
                    }

                )
            }
        }
    }

    TextAlertDialog(
        show = showConfirmationDialog,
        title = dialogTitle,
        message = dialogMessage,
        confirmText = "Confirm",
        onConfirm = {
            longPressedItemId?.let { viewModel.delete(it) }
            SnackbarManager.show("Movie deleted ${actionSheetItem?.title}")
        },
        onDismiss = {
            showConfirmationDialog = false
        }
    )

    ActionBottomSheet(
        showActions = false,
        sheetState = sheetState,
        onCancel = {
            scope.launch { sheetState.hide() }
        },
        onConfirm = {
            scope.launch { sheetState.hide() }
        }
    ) {

        if (actionSheetItem != null) {
            WatchlistItemOptionsSheetContent(
                selectedMovieItem = actionSheetItem,
                hideSheet = { scope.launch { sheetState.hide() } },
                onMovieDelete = { id ->
                    id?.let { deleteMovieFun(it) }
                },
                onMovieFinish = { id ->
                    if (id != null) {
                        dialogTitle = "Rate this movie"
                        longPressedItemId = id
                        showRatingDialog = true
                    }
                },
                onUpdateRating = { rating, id ->
                    dialogTitle = "Update rating"
                    longPressedItemId = id
                    originalRating = rating
                    updateRating = true
                    showRatingDialog = true
                }
            )
        }
    }


    DialogBasic(
        show = showRatingDialog,
        title = dialogTitle,
        showDefaultActions = false,
        onDismiss = {
            showRatingDialog = false
            updateRating = false
        },
        content = {
            RateMovieDialogContent(
                onCancel = { showRatingDialog = false },
                updateRating = updateRating,
                originalRating = originalRating,
                onConfirm = { rating ->
                    longPressedItemId?.let {
                        viewModel.setUserRating(it, rating)
                        viewModel.finish(it)
                    }
                    if (updateRating) {
                        SnackbarManager.show("User rating updated")
                    }
                },
            )
        }
    )
}


