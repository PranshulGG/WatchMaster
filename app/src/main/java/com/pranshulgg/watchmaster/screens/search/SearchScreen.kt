package com.pranshulgg.watchmaster.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.helpers.provideWatchlistRepository
import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModelFactory
import com.pranshulgg.watchmaster.screens.search.ui.AddToWatchlistSheetContent
import com.pranshulgg.watchmaster.screens.search.ui.SearchFloatingBarContent
import com.pranshulgg.watchmaster.screens.search.ui.SearchRow
import com.pranshulgg.watchmaster.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.ui.components.BottomNav
import com.pranshulgg.watchmaster.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.utils.NavigateUpBtn
import com.pranshulgg.watchmaster.utils.Symbol
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModelFactory(LocalContext.current)),
    navController: NavController,
    type: SearchType
) {
    val query = viewModel.query
    val results = viewModel.results
    val loading = viewModel.loading

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val selectedItem = remember { mutableStateOf<SearchItem?>(null) }

    val repositoryWatchList = provideWatchlistRepository(LocalContext.current)

    val viewModelWatchList: WatchlistViewModel = viewModel(
        factory = WatchlistViewModelFactory(repositoryWatchList)
    )
    val systemInsets = WindowInsets.systemBars.asPaddingValues()

    val scrollBehaviorToolBar =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)



    LaunchedEffect(viewModel.showNoResultsSnack) {
        if (viewModel.showNoResultsSnack) {
            SnackbarManager.show("No matches found. Try a different title")
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            LargeFlexibleTopAppBar(
                title = { Text(text = "Search") },
                navigationIcon = { NavigateUpBtn(navController) },
                scrollBehavior = scrollBehavior,
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
            )
        },
        bottomBar = {

            Box(
                Modifier
                    .fillMaxWidth()
                    .imePadding()
            ) {

                HorizontalFloatingToolbar(
                    scrollBehavior = scrollBehaviorToolBar,
                    contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp, start = 10.dp),
                    modifier = Modifier
                        .padding(
                            bottom = systemInsets.calculateBottomPadding()
                                    + ScreenOffset,
                            top = ScreenOffset
                        )
                        .height(64.dp)
                        .align(Alignment.BottomCenter)
                        .zIndex(1f),
                    colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
                    expanded = true,
                    floatingActionButton = {
                        FloatingToolbarDefaults.VibrantFloatingActionButton(
                            onClick = {
                                viewModel.search(type)
                                focusManager.clearFocus()
                            }
                        ) {
                            Symbol(
                                R.drawable.search_24px,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    },
                    content = {
                        SearchFloatingBarContent(
                            viewModel,
                            query,
                            focusRequester,
                            focusManager,
                            type
                        )
                    })
            }

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                )
        ) {
            when {
                loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator(modifier = Modifier.size(60.dp))
                    }
                }

                results.isEmpty() -> {
                    EmptyContainerPlaceholder(
                        R.drawable.search_24px,
                        if (type == SearchType.MOVIE) "Search movies" else "Search tv series",
                        description = "Search through the database"
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .nestedScroll(scrollBehaviorToolBar)
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        itemsIndexed(results) { index, item ->
                            SearchRow(item, index, results, onAddToWatchlist = {
                                scope.launch {
                                    if (viewModelWatchList.exists(item.id)) {
                                        SnackbarManager.show("Already in watchlist")
                                    } else {

                                        selectedItem.value = item
                                        if (item.mediaType == "tv") {
                                            viewModel.fetchSeasonData(item.id)
                                        }
                                        sheetState.show()

                                    }
                                }
                            }
                            )

                            if (index == results.lastIndex) {
                                Spacer(Modifier.height(32.dp))
                            }
                        }

                    }
                }
            }
        }
    }


    val closeSheet = {
        scope.launch {
            sheetState.hide()
        }
    }
    ActionBottomSheet(
        showActions = false,
        sheetState = sheetState,
        onCancel = {
        },
        onConfirm = {
        }
    ) {
        val item = selectedItem.value
        if (item != null) {
            AddToWatchlistSheetContent(
                item = item,
                seasonLoading = viewModel.seasonLoading,
                seasonData = viewModel.seasonData,
                onCancel = {
                    closeSheet()
                },
                onConfirm = {
                    viewModel.addToWatchlist(item, viewModel.seasonData)
                    SnackbarManager.show(
                        "Added to watchlist",
                        actionLabel = "View"
                    ) { navController.popBackStack() }
                    closeSheet()
                }
            )
        }
    }


}



