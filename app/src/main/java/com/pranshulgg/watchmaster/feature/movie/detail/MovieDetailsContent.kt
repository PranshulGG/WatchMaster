package com.pranshulgg.watchmaster.feature.movie.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
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
                CastMovieSection(movieItem)
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }

}