package com.pranshulgg.watchmaster.feature.movie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.finished.FinishedMovies
import com.pranshulgg.watchmaster.feature.movie.watching.WatchingMovies
import com.pranshulgg.watchmaster.feature.movie.watchlist.WatchlistMovies
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieTabsContent(
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
    state: MovieHomeState,
    onLongActionMovieRequest: (WatchlistItemEntity) -> Unit
) {

    val tabs = MovieTab.entries
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
                MovieTab.WATCHLIST ->
                    WatchlistMovies(
                        state.isLoading,
                        state.watchlist,
                        scrollBehavior,
                        scrollBehaviorTopBar,
                        navController,
                        onLongActionMovieRequest = { item ->
                            onLongActionMovieRequest(item)
                        }
                    )

                MovieTab.WATCHING -> WatchingMovies(
                    state.isLoading,
                    state.watching,
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController,
                    onLongActionMovieRequest = { item ->
                        onLongActionMovieRequest(item)
                    }
                )

                MovieTab.FINISHED -> FinishedMovies(
                    state.isLoading,
                    state.finished,
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController,
                    onLongActionMovieRequest = { item ->
                        onLongActionMovieRequest(item)
                    }

                )
            }
        }
    }
}