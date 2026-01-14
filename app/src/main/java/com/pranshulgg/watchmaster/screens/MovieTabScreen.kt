package com.pranshulgg.watchmaster.screens

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
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieTabScreen(navController: NavController, motionScheme: MotionScheme) {

    val tabs = listOf("Watchlist", "Watching", "Finished")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        topBar = {
            PrimaryTabRow(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
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
                        text = { Text(title, fontSize = 17.sp) }
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
                ExtendedFloatingActionButton(
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
                0 -> WatchlistMovies()
                1 -> WatchingMovies()
                2 -> FinishedMovies()
            }
        }
    }
}

@Composable
fun WatchlistMovies() {
    Text("Movies you want to watch 👀", modifier = Modifier.padding(16.dp))
}

@Composable
fun WatchingMovies() {
    Text("Currently watching 🎬", modifier = Modifier.padding(16.dp))
}

@Composable
fun FinishedMovies() {
    Text("Completed movies ⭐", modifier = Modifier.padding(16.dp))
}
