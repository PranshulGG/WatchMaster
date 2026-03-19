package com.pranshulgg.watchmaster.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.feature.main.components.MainFloatingToolbar
import com.pranshulgg.watchmaster.feature.movie.MovieHomeScreen
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.TooltipIconBtn
import com.pranshulgg.watchmaster.feature.home.HomeScreen
import com.pranshulgg.watchmaster.feature.tv.TvHomeScreen

@OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    navController: NavController,
    motionScheme: MotionScheme,
) {
    val prefs = LocalAppPrefs.current

    val viewModel: MainScreenNavViewModel =
        viewModel(factory = MainScreenNavViewModelFactory(prefs.defaultTab))

    val selectedItem = viewModel.selectedItem
    val appBarTitles = listOf("Home", "Movies", "TV series")

    val scrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    val scrollBehaviorTopBar = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehaviorTopBar,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                title = {
                    Text(appBarTitles[selectedItem])
                },
                actions = {
                    TooltipIconBtn(
                        onClick = { navController.navigate(NavRoutes.MOVIE_LISTS_SCREEN) },
                        icon = R.drawable.list_alt_24px,
                        tooltipText = if (appBarTitles[selectedItem] == "Movies") "Movie lists" else "TV lists"
                    )
                    TooltipIconBtn(
                        onClick = { navController.navigate(NavRoutes.SETTINGS) },
                        icon = R.drawable.settings_24px,
                        tooltipText = "Settings"
                    )
                }
            )
        },
        bottomBar = {
            MainFloatingToolbar(
                selectedItem = selectedItem,
                onItemSelected = { index -> viewModel.selectedItem = index },
                navController,
                scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {

            when (selectedItem) {
                0 -> HomeScreen(
                    navController
                )

                1 -> MovieHomeScreen(
                    navController,
                    scrollBehavior,
                    scrollBehaviorTopBar,
                )

                2 -> {
                    TvHomeScreen(
                        navController,
                        scrollBehavior,
                        scrollBehaviorTopBar,
                    )
                }
            }
        }
    }
}