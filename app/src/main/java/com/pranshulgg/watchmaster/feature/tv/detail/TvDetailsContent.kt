package com.pranshulgg.watchmaster.feature.tv.detail

import java.util.concurrent.TimeUnit
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.CastTvSection
import com.pranshulgg.watchmaster.feature.shared.media.components.NotesSection
import com.pranshulgg.watchmaster.feature.shared.media.components.OverviewSection
import com.pranshulgg.watchmaster.feature.tv.detail.components.EpisodesSection
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvHeroHeader
import kotlinx.coroutines.launch
import kotlin.text.get

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvDetailsContent(
    tvItem: TvBundle,
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    season: SeasonEntity?,
    viewModel: TvDetailsViewModel,
    watchlistViewModel: WatchlistViewModel,
    episodes: List<TvEpisodeEntity>
) {


    val isFinished = season?.status == WatchStatus.FINISHED

    val watchlistFlow = remember(tvItem.id) { watchlistViewModel.item(tvItem.id) }
    val watchlistItem by watchlistFlow.collectAsStateWithLifecycle()


    val cachedHrs = season?.cachedAt?.let { TimeUnit.MILLISECONDS.toHours(it) }
        ?: TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis())
    val currentHrs = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis())

    val refreshUnlocked = currentHrs.minus(cachedHrs) > 24

    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()


    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = {
            isRefreshing = true
            
            if (refreshUnlocked && season != null) {
                SnackbarManager.show("Fetching...")
                viewModel.refreshSeasonData(season)
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
        ) {
            item {
                if (season != null) {
                    TvHeroHeader(
                        tvItem,
                        watchlistItem,
                        navController,
                        isFinished,
                        season,
                        userRating = season.seasonUserRating,
                        onUpdateRating = { newRating ->
                            watchlistViewModel.setSeasonUserRating(season.seasonId, newRating)
                            SnackbarManager.show("User rating updated")
                        }
                    )


                    MediaStatusSection(
                        status = season.status,
                        onClick = { viewModel.showWatchProviderSheet(tvItem.watchProviders?.results["US"]) })
                    OverviewSection(tvItem.overview)
                    NotesSection(
                        season.seasonNotes.isNullOrBlank(),
                        { viewModel.showNoteDialog(season.seasonNotes ?: "") },
                        season.seasonNotes ?: ""
                    )
                    if (episodes.isNotEmpty()) {
                        EpisodesSection(
                            episodes,
                            viewModel,
                            season.seasonId,
                            season.seasonProgress ?: 0,
                            season.status
                        )
                    }
                    CastTvSection(tvItem)
                    Spacer(modifier = Modifier.height(56.dp))
                } else {
                    Text("No season found")
                }
            }
        }
    }
}