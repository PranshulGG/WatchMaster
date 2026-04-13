package com.pranshulgg.watchmaster.feature.movie.detail

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.LoadingIndicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieHeroHeader
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.CastMovieSection
import com.pranshulgg.watchmaster.feature.shared.media.components.NotesSection
import com.pranshulgg.watchmaster.feature.shared.media.components.OverviewSection

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieDetailsContent(
    movieItem: MovieBundle,
    watchlistItem: WatchlistItemEntity?,
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    viewModel: MovieDetailsViewModel,
    watchlistViewModel: WatchlistViewModel
) {
    val isFinished = watchlistItem?.status == WatchStatus.FINISHED
    val cachedHrs = movieItem.cachedAt / 1000 / 60 / 60
    val currentHrs = System.currentTimeMillis() / 1000 / 60 / 60

    val refreshUnlocked = currentHrs.minus(cachedHrs) > 24

    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()


    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = {
            isRefreshing = true

            if (refreshUnlocked) {
                SnackbarManager.show("Fetching...")
                viewModel.load(
                    movieItem.id,
                    forceFetch = true,
                    onError = {
                        SnackbarManager.show("Failed to fetch movie data")
                    })
                isRefreshing = false
            } else {
                SnackbarManager.show("Data is already up to date")
                isRefreshing = false
            }
        },
        indicator = {
            LoadingIndicator(
                pullToRefreshState,
                isRefreshing,
                modifier = Modifier
                    .zIndex(99999f)
                    .align(Alignment.TopCenter)
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior)
    ) {
        LazyColumn(
            modifier = Modifier
                .nestedScroll(scrollBehavior)
                .imePadding()
        )
        {

            item {
                if (watchlistItem != null) {
                    MovieHeroHeader(
                        movieItem,
                        watchlistItem,
                        navController,
                        isFinished,
                        watchlistItem.userRating,
                        onUpdateRating = { newRating ->
                            watchlistViewModel.setUserRating(watchlistItem.id, newRating)
                            SnackbarManager.show("User rating updated")
                        }
                    )
                    MediaStatusSection(
                        status = watchlistItem.status,
                        onClick = { viewModel.showWatchProviderSheet(movieItem.watchProviders?.results["US"]) })
                    OverviewSection(movieItem.overview)
                    NotesSection(
                        watchlistItem.notes.isNullOrBlank(),
                        { viewModel.showNoteDialog(watchlistItem.notes ?: "") },
                        watchlistItem.notes ?: ""
                    )
                    CastMovieSection(movieItem, onCastClick = { personId ->
                        navController.navigate(
                            NavRoutes.personScreen(personId)
                        )
                    })
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }

}