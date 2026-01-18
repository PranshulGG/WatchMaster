package com.pranshulgg.watchmaster.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.screens.search.ui.AddToWatchlistSheetContent
import com.pranshulgg.watchmaster.screens.search.ui.SearchBottomSheetContent
import com.pranshulgg.watchmaster.screens.search.ui.SearchRow
import com.pranshulgg.watchmaster.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.utils.NavigateUpBtn
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModelFactory(LocalContext.current)),
    navController: NavController
) {
    val query = viewModel.query
    val results = viewModel.results
    val loading = viewModel.loading


    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val selectedItem = remember { mutableStateOf<SearchItem?>(null) }
    val showDialog = rememberSaveable { mutableStateOf(false) }

    BottomSheetScaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,

        sheetShadowElevation = 10.dp,
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
        sheetContent = {
            SearchBottomSheetContent(viewModel, query, focusRequester, focusManager)
        },
        sheetPeekHeight = 100.dp,
        sheetDragHandle = null,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding() - 20.dp
                )
        ) {
            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }

                results.isEmpty() -> {
                    EmptyContainerPlaceholder(
                        R.drawable.search_24px,
                        "Search movies",
                        description = "Search through the movie database"
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        itemsIndexed(results) { index, item ->
                            SearchRow(item, index, results, onAddToWatchlist = {
                                selectedItem.value = item
                                showDialog.value = true
                            })
                            if (index == results.lastIndex) {
                                Spacer(Modifier.height(32.dp))
                            }
                        }

                    }
                }
            }
        }
    }

//    ActionBottomSheet(
//        sheetState = addToWatchlistSheetState,
//        onCancel = {
//            scope.launch { addToWatchlistSheetState.hide() }
//            selectedItem.value = null
//        },
//        onConfirm = {
//            selectedItem.value?.let {
//                viewModel.addToWatchlist(it)
//            }
//            scope.launch { addToWatchlistSheetState.hide() }
//            selectedItem.value = null
//
//        },
//        confirmText = "Save to watchlist"
//    ) {
//
//        selectedItem.value?.let { item ->
//            AddToWatchlistSheetContent(item)
//        }
//
//    }

    if (showDialog.value && selectedItem.value != null) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
                selectedItem.value = null
            }
        ) {

            Surface(
                modifier = Modifier
                    .width(300.dp),
                shape = RoundedCornerShape(26.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shadowElevation = 6.dp
            ) {
                AddToWatchlistSheetContent(
                    item = selectedItem.value!!
                )
            }
        }
    }


}



