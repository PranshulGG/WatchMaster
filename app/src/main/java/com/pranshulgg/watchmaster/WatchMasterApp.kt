package com.pranshulgg.watchmaster

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pranshulgg.watchmaster.core.prefs.AppPrefs
import com.pranshulgg.watchmaster.core.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.core.ui.navigation.AppNavHost
import com.pranshulgg.watchmaster.core.ui.navigation.NavTransitions
import com.pranshulgg.watchmaster.core.ui.snackbar.LocalSnackbarHostState
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.WatchMasterTheme
import com.pranshulgg.watchmaster.feature.main.MainScreen
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailPage
import com.pranshulgg.watchmaster.feature.search.SearchScreen
import com.pranshulgg.watchmaster.feature.search.SearchType
import com.pranshulgg.watchmaster.feature.search.SearchViewModel
import com.pranshulgg.watchmaster.feature.search.SearchViewModelFactory
import com.pranshulgg.watchmaster.feature.setting.SettingsScreen
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WatchMasterApp() {
    val navController = rememberNavController()
    val motionScheme = MaterialTheme.motionScheme
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
            AppNavHost(
                navController = navController,
                snackbarHostState = snackbarHostState,
                motionScheme = motionScheme,
                context = context
            )
        }
    }

}