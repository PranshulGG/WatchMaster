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
import com.pranshulgg.watchmaster.screens.movieTabs.watching.WatchingMovies
import com.pranshulgg.watchmaster.screens.movieTabs.watchlist.WatchlistMovies

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
        contentWindowInsets = WindowInsets(bottom = 0, top = 0),
    ) { innerPadding ->


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                0 -> WatchlistMovies(
                    items = items.filter {
                        it.status == WatchStatus.WANT_TO_WATCH && it.mediaType != "tv"
                    },
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController,
                )

                1 -> WatchingMovies(
                    items = items.filter {
                        (it.status == WatchStatus.WATCHING || it.status == WatchStatus.INTERRUPTED) && it.mediaType != "tv"

                    },
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController

                )

                2 -> FinishedMovies(
                    items = items.filter {
                        it.status == WatchStatus.FINISHED && it.mediaType != "tv"
                    },
                    scrollBehavior,
                    scrollBehaviorTopBar,
                    navController

                )
            }
        }
    }
}


