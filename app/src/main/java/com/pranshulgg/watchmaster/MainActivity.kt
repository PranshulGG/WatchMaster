package com.pranshulgg.watchmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pranshulgg.watchmaster.helpers.PreferencesHelper
import com.pranshulgg.watchmaster.prefs.AppPrefs
import com.pranshulgg.watchmaster.prefs.AppPrefs.initPrefs
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.screens.MainScreen
import com.pranshulgg.watchmaster.screens.SettingsPage
import com.pranshulgg.watchmaster.screens.search.SearchScreen
import com.pranshulgg.watchmaster.screens.search.SearchViewModel
import com.pranshulgg.watchmaster.screens.search.SearchViewModelFactory
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
                    useExpressive = prefs.useExpressive,
                    seedColor = Color(prefs.themeColor.toColorInt())
                ) {
                    NavHost(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                        navController = navController, startDestination = "search",
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
                            "main",
                        ) {
                            MainScreen(navController, motionScheme = motionScheme)
                        }
                        composable(
                            "settingsPage",
                        ) {
                            SettingsPage(navController)
                        }
                        composable("search") {

                            val viewModel: SearchViewModel = viewModel(
                                factory = SearchViewModelFactory(context)
                            )

                            SearchScreen(
                                viewModel = viewModel,
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}


