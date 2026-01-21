package com.pranshulgg.watchmaster.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.models.HomeNavViewModel
import com.pranshulgg.watchmaster.ui.components.BottomNav
import com.pranshulgg.watchmaster.ui.components.Tooltip
import com.pranshulgg.watchmaster.utils.Symbol

@OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: HomeNavViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    motionScheme: MotionScheme,
) {

    val selectedItem = viewModel.selectedItem
    val appBarTitles = listOf("Home", "Movies", "TV series")
    val context = LocalContext.current

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
                    Tooltip(
                        "Settings",
                        preferredPosition = TooltipAnchorPosition.Below,
                        spacing = 10.dp
                    ) {
                        IconButton(
                            onClick = { navController.navigate(NavRoutes.SETTINGS) },
                            shapes = IconButtonDefaults.shapes()
                        ) {
                            Symbol(
                                R.drawable.settings_24px,
                                desc = "settings icon",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNav(
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
                0 -> HomeTabScreen()
                1 -> MovieTabHomeScreen(
                    navController,
                    motionScheme,
                    scrollBehavior,
                    scrollBehaviorTopBar
                )
            }

        }
    }


}