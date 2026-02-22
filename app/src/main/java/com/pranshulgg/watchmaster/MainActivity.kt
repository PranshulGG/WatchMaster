package com.pranshulgg.watchmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
import com.pranshulgg.watchmaster.ui.snackbar.LocalSnackbarHostState
import com.pranshulgg.watchmaster.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.ui.theme.WatchMasterTheme
import com.pranshulgg.watchmaster.utils.NavTransitions
import kotlinx.coroutines.flow.collectLatest

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
                SnackbarManager.events.collectLatest { event ->
                    snackbarHostState.currentSnackbarData?.dismiss()

                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                        withDismissAction = event.actionLabel == null,
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        event.onAction?.invoke()
                    }
                }
            }



            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState,
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
                    dynamicColor = prefs.useDynamicColor,
                    seedColor = Color(prefs.themeColor.toColorInt())
                ) {
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            Modifier
                                .fillMaxWidth()
                                .zIndex(1f)
                                .align(Alignment.BottomCenter)
                                .padding(
                                    bottom =
                                        WindowInsets.navigationBars
                                            .asPaddingValues()
                                            .calculateBottomPadding()
                                )
                        )
                        NavHost(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceContainer),
                            navController = navController,
//                            startDestination = NavRoutes.mediaDetail(10681),
                            startDestination = NavRoutes.SEARCH,
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
                                MediaDetailPage(id = id, navController)
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


