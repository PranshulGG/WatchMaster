package com.pranshulgg.watchmaster.core.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pranshulgg.watchmaster.feature.main.MainScreen
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailPage
import com.pranshulgg.watchmaster.feature.lists.MovieListsScreen
import com.pranshulgg.watchmaster.feature.lists.listEntry.ListEntryScreen
import com.pranshulgg.watchmaster.feature.lists.view.ViewListScreen
import com.pranshulgg.watchmaster.feature.person.PersonScreen
import com.pranshulgg.watchmaster.feature.search.SearchScreen
import com.pranshulgg.watchmaster.feature.search.SearchType
import com.pranshulgg.watchmaster.feature.setting.SettingsScreen
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsScreen

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    motionScheme: MotionScheme,
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
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
        )
        NavHost(
            navController = navController,
            startDestination = NavRoutes.MAIN,
//            startDestination = NavRoutes.personScreen(287),
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
            enterTransition = { NavTransitions.enter() },
            exitTransition = { NavTransitions.exit() },
            popEnterTransition = { NavTransitions.popEnter() },
            popExitTransition = { NavTransitions.popExit() }
        ) {
            composable(
                NavRoutes.MAIN
            ) {
                MainScreen(navController)
            }
            composable(
                NavRoutes.SETTINGS
            ) {
                SettingsScreen(navController)
            }
            composable(
                route = "${NavRoutes.MOVIE_DETAIL_SCREEN}/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getLong("id")
                MovieDetailPage(id = id, navController)
            }
            composable(
                route = "${NavRoutes.SEARCH}?searchType={searchType}",
                arguments = listOf(
                    navArgument("searchType") {
                        type = NavType.StringType
                        defaultValue = SearchType.MOVIE.name
                    }
                )
            ) { backStackEntry ->

                val searchType = backStackEntry.arguments
                    ?.getString("searchType")
                    ?.let { SearchType.valueOf(it) }
                    ?: SearchType.MULTI

                SearchScreen(
                    navController = navController,
                    searchType = searchType
                )
            }
            composable(
                route = "${NavRoutes.TV_DETAIL_SCREEN}/{id}/{seasonNumber}/{seasonId}",
                arguments = listOf(
                    navArgument("id") { type = NavType.LongType },
                    navArgument("seasonNumber") { type = NavType.IntType },
                    navArgument("seasonId") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getLong("id")
                val seasonNumber = backStackEntry.arguments!!.getInt("seasonNumber")
                val seasonId = backStackEntry.arguments!!.getLong("seasonId")

                TvDetailsScreen(
                    id = id,
                    seasonNumber = seasonNumber,
                    navController = navController,
                    seasonId = seasonId
                )
            }
            composable(
                NavRoutes.LISTS_SCREEN
            ) {
                MovieListsScreen(navController)
            }
            composable(
                route = "${NavRoutes.LISTS_ENTRY_SCREEN}/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: -1L
                ListEntryScreen(id, navController)

            }
            composable(
                route = "${NavRoutes.LISTS_VIEW_SCREEN}/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getLong("id")
                ViewListScreen(navController, id)

            }
            composable(
                route = "${NavRoutes.PERSON_SCREEN}/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments!!.getLong("id")
                PersonScreen(id = id, navController)
            }
        }
    }

}