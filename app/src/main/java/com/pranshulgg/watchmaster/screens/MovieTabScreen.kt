package com.pranshulgg.watchmaster.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Symbol
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.utils.Symbol
import kotlinx.coroutines.launch
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.getGenreNames
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModelFactory

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieTabScreen(navController: NavController, motionScheme: MotionScheme) {

    val tabs = listOf("Watchlist", "Watching", "Finished")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            PrimaryTabRow(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                selectedTabIndex = pagerState.currentPage
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
        contentWindowInsets = WindowInsets(bottom = 0),
        floatingActionButton = {
            AnimatedVisibility(
                visible = pagerState.currentPage == 0,
                enter = scaleIn(
                    initialScale = 0.8f,
                    animationSpec = motionScheme.defaultSpatialSpec()
                ) + fadeIn(),
                exit = scaleOut(
                    targetScale = 0.8f,
                    animationSpec = motionScheme.defaultSpatialSpec()
                ) + fadeOut(),
            ) {
                SmallExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    elevation = FloatingActionButtonDefaults.elevation(
                        0.dp,
                        pressedElevation = 0.dp,
                    ),
                    onClick = {
                        navController.navigate("search")
                    },
                    icon = {
                        Symbol(
                            R.drawable.add_24px,
                            size = 30.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    text = { Text("Add Movie") }
                )
            }

        },
    ) { innerPadding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> WatchlistMovies(context)
                1 -> WatchingMovies()
                2 -> FinishedMovies()
            }
        }
    }
}

@Composable
fun WatchlistMovies(context: Context) {
    val db = WatchMasterDatabase.getInstance(context)
    val repository = WatchlistRepository(db.watchlistDao())

    val viewModel: WatchlistViewModel = viewModel(
        factory = WatchlistViewModelFactory(repository)
    )

    val items by viewModel.watchlist.collectAsState()

    if (items.isEmpty()) {
        Text("No movies yet 👀")
        return
    }

    LazyColumn {
        items(items) { item ->
            WatchlistRow(item)
        }
    }
}


@Composable
fun WatchlistRow(item: WatchlistItemEntity) {
    Text(
        text = getGenreNames(item.genreIds ?: emptyList()).joinToString(", "),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun WatchingMovies() {
    Text("Currently watching 🎬", modifier = Modifier.padding(16.dp))
}

@Composable
fun FinishedMovies() {
    Text("Completed movies ⭐", modifier = Modifier.padding(16.dp))
}
