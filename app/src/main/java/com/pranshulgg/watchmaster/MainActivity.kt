package com.pranshulgg.watchmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.helpers.PreferencesHelper
import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.prefs.AppPrefs
import com.pranshulgg.watchmaster.prefs.AppPrefs.initPrefs
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.screens.MainScreen
import com.pranshulgg.watchmaster.screens.SettingsPage
import com.pranshulgg.watchmaster.screens.media_detail.MediaDetailPage
import com.pranshulgg.watchmaster.screens.search.SearchScreen
import com.pranshulgg.watchmaster.screens.search.SearchViewModel
import com.pranshulgg.watchmaster.screens.search.SearchViewModelFactory
import com.pranshulgg.watchmaster.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.ui.theme.WatchMasterTheme
import com.pranshulgg.watchmaster.utils.NavTransitions

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        initPrefs(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentMotionScheme = motionScheme
            val motionScheme = remember(currentMotionScheme) { currentMotionScheme }
            val context = LocalContext.current

            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                SnackbarManager.messages.collect { message ->
                    snackbarHostState.showSnackbar(message)
                }
            }


            CompositionLocalProvider(
                LocalAppPrefs provides AppPrefs.state()
            ) {
                val prefs = LocalAppPrefs.current

                val appTheme = when (prefs.appTheme) {
                    "Dark" -> true
                    "Light" -> false
                    "System" -> isSystemInDarkTheme()
                    else -> isSystemInDarkTheme()
                }

                WatchMasterTheme(
                    darkTheme = appTheme,
                    themeVariantType = prefs.themeVariant,
                    seedColor = Color(prefs.themeColor.toColorInt())
                ) {
                    androidx.compose.material3.Scaffold(
                        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
                    ) { innerPad ->
                        NavHost(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .padding(innerPad.calculateRightPadding(layoutDirection = LayoutDirection.Ltr)),
                            navController = navController,
//                            startDestination = NavRoutes.mediaDetail(5174),
                            startDestination = NavRoutes.MAIN,
                            enterTransition = {
                                NavTransitions.enter(motionScheme)
                            },
                            exitTransition = {
                                NavTransitions.exit(motionScheme)

                            },
                            popEnterTransition = {
                                NavTransitions.popEnter(motionScheme)

                            },
                            popExitTransition = {
                                NavTransitions.popExit(motionScheme)
                            }
                        ) {
                            composable(
                                NavRoutes.MAIN,
                            ) {
                                MainScreen(
                                    navController,
                                    motionScheme = motionScheme,
                                )
                            }
                            composable(
                                NavRoutes.SETTINGS,
                            ) {
                                SettingsPage(navController)
                            }
                            composable(
                                route = "${NavRoutes.MEDIA_DETAIL_PAGE}/{id}",
                                arguments = listOf(
                                    navArgument("id") { type = NavType.LongType }
                                )
                            ) { backStackEntry ->
                                val id = backStackEntry.arguments!!.getLong("id")
                                MediaDetailPage(movieId = id, navController)
                            }

                            composable(
                                route = "${NavRoutes.SEARCH}?type={type}",
                                arguments = listOf(
                                    navArgument("type") {
                                        type = NavType.StringType
                                        defaultValue = SearchType.MULTI.name
                                    }
                                )
                            ) { backStackEntry ->

                                val type = backStackEntry.arguments
                                    ?.getString("type")
                                    ?.let { SearchType.valueOf(it) }
                                    ?: SearchType.MULTI

                                val viewModel: SearchViewModel = viewModel(
                                    factory = SearchViewModelFactory(context)
                                )

                                SearchScreen(
                                    viewModel = viewModel,
                                    navController = navController,
                                    type = type
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}


