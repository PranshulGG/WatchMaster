package com.pranshulgg.watchmaster.feature.tv

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.MovieTab
import com.pranshulgg.watchmaster.feature.movie.finished.FinishedMovies
import com.pranshulgg.watchmaster.feature.movie.watching.WatchingMovies
import com.pranshulgg.watchmaster.feature.movie.watchlist.WatchlistMovies
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.finished.FinishedTv
import com.pranshulgg.watchmaster.feature.tv.watching.WatchingTv
import com.pranshulgg.watchmaster.feature.tv.watchlist.WatchlistTv
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvTabsContent(
    navController: NavController, state: TvHomeState,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
    viewModel: WatchlistViewModel
) {

    val tabs = TvTab.entries
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()


    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            PrimaryTabRow(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                selectedTabIndex = pagerState.currentPage,
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }

                        },
                        text = { Text(tab.title) }
                    )
                }
            }
        },
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->


            when (tabs[page]) {
                TvTab.WATCHLIST -> {
                    WatchlistTv(
                        state.isLoading,
                        state.watchlist,
                        scrollBehavior,
                        scrollBehaviorTopBar,
                        navController,
                        onLongActionTvRequest = {},
                        viewModel
                    )
                }

                TvTab.WATCHING -> {
                    WatchingTv(state.isLoading, state.watching)
                }

                TvTab.FINISHED -> {
                    FinishedTv(state.isLoading, state.finished)
                }
            }

        }
    }
}