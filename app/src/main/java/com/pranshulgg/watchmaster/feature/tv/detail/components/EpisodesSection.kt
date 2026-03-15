package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsEpisodeInfoSheet
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EpisodesSection(
    episodes: List<TvEpisodeEntity>,
    viewModel: TvDetailsViewModel,
    seasonStatus: WatchStatus
) {

    var expanded by remember { mutableStateOf(false) } // DEFAULT: FALSE
    var showSheet by remember { mutableStateOf(false) }
    var currentEp by remember { mutableStateOf<TvEpisodeEntity?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    val watchedProgress = remember(episodes) {
        val watchedItems = episodes.count { it.isWatched }
        (watchedItems.toFloat() / episodes.size) * 100
    }

    LaunchedEffect(watchedProgress) {
        if (episodes.isNotEmpty()) {
            viewModel.updateSeasonProgress(
                episodes[0].seasonId,
                watchedProgress.roundToInt()
            )
        }
    }


    MediaSectionCard(
        title = "Episodes",
        titleIcon = R.drawable.list_alt_24px,
        actionText = if (expanded) "Hide episodes" else "Show episodes",
        actionOnClick = {
            expanded = !expanded
        },
        showAction = true
    ) {
        if (expanded) {
            Column(
                modifier = Modifier.padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                episodes.forEach { episode ->
                    EpisodeItem(
                        episode,
                        viewModel,
                        seasonStatus,
                        onTrailingAction = {
                            currentEp = episode
                            showSheet = true
                        }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    TvDetailsEpisodeInfoSheet(
        show = showSheet,
        episode = currentEp,
        onDismiss = {
            showSheet = false
        },
        onConfirm = {
            currentEp?.let {
                if (seasonStatus == WatchStatus.WANT_TO_WATCH || seasonStatus == WatchStatus.FINISHED) {
                    SnackbarManager.show("Please mark the season as 'Watching' to track episodes")
                    return@let
                }
                if (it.isWatched) {
                    viewModel.markEpUnWatched(it.epId)
                } else {
                    viewModel.markEpWatched(it.epId)
                }
            }
            showSheet = false
        },
        sheetState = sheetState
    )

}