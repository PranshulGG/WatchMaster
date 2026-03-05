package com.pranshulgg.watchmaster.feature.movie.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.media.CastItem
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieDetailFloatingToolBar
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieHeroHeader
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.ui.FloatingToolbarMediaActionsParams
import com.pranshulgg.watchmaster.feature.shared.media.ui.MediaActionsFloatingToolbar
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsContent
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieDetailsScaffold(
    id: Long,
    scrollBehavior: FloatingToolbarScrollBehavior,
    viewModel: MovieDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    navController: NavController
) {

    val watchlistFlow = remember(id) { watchlistViewModel.item(id) }
    val watchlistItem by watchlistFlow.collectAsStateWithLifecycle()

    val movieItem = viewModel.state

    val loading = viewModel.loading
    val isMoviePinned = watchlistItem?.isPinned == true

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            if (!loading) {
                MediaActionsFloatingToolbar(
                    scrollBehavior,
                    watchlistItem?.status ?: WatchStatus.WATCHING,
                    actions = FloatingToolbarMediaActionsParams(
                        startWatching = { watchlistViewModel.start(id) },
                        resetWatching = { watchlistViewModel.reset(id) },
                        finishWatching = { viewModel.showRatingDialog() },
                        interruptWatching = { watchlistViewModel.interrupt(id) },
                        delete = { viewModel.showConfirmationDialog() },
                        togglePin = { watchlistViewModel.setPinned(id, !isMoviePinned) }
                    ),
                    isMoviePinned
                )
            }
        },

        ) { paddingValues ->

        if (loading) {
            LoadingScreenPlaceholder()
            Box(modifier = Modifier.padding(paddingValues))
        }

        movieItem?.let { item ->
            MovieDetailsContent(
                item,
                watchlistItem,
                navController,
                scrollBehavior,
                viewModel,
                watchlistViewModel,
            )
        }
    }

}