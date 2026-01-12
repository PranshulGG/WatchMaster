package com.pranshulgg.watchmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pranshulgg.watchmaster.helpers.PreferencesHelper
import com.pranshulgg.watchmaster.prefs.AppPrefs
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.screens.MainScreen
import com.pranshulgg.watchmaster.ui.theme.WatchMasterTheme
import com.pranshulgg.watchmaster.utils.NavTransitions

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesHelper.init(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentMotionScheme = motionScheme
            val motionScheme = remember(currentMotionScheme) { currentMotionScheme }


            CompositionLocalProvider(
                LocalAppPrefs provides AppPrefs.state()
            ) {
                val prefs = LocalAppPrefs.current
                WatchMasterTheme(
                    darkTheme = prefs.darkTheme,
                    useExpressive = prefs.useExpressive
                ) {
                    NavHost(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        navController = navController, startDestination = "main",
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
                            MainScreen(navController)

                        }
                    }
                }
            }
        }
    }
}


