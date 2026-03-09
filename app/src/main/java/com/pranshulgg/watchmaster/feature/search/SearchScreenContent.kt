package com.pranshulgg.watchmaster.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.feature.search.components.SearchRow
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    paddingValues: PaddingValues,
    viewModel: SearchViewModel,
    searchType: SearchType,
    scrollBehaviorToolbar: FloatingToolbarScrollBehavior,
    scrollBehavior: TopAppBarScrollBehavior,
    watchlistViewModel: WatchlistViewModel
) {

    val results = viewModel.results
    val loading = viewModel.loading
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.showNoResultsSnack) {
        if (viewModel.showNoResultsSnack) {
            SnackbarManager.show("No matches found. Try a different title")
        }
    }

    Box(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
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
                    if (searchType == SearchType.MOVIE) "Search movies" else "Search tv series",
                    description = "Search through the database"
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .nestedScroll(scrollBehaviorToolbar)
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(results) { index, item ->
                        SearchRow(item, index, results, onSearchItemClick = {
                            scope.launch { viewModel.onSearchItemClick(item, watchlistViewModel) }
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