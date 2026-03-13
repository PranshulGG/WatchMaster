package com.pranshulgg.watchmaster.feature.tv.detail.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel
import kotlin.math.round
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EpisodesSection(
    episodes: List<TvEpisodeEntity>,
    viewModel: TvDetailsViewModel,
    seasonStatus: WatchStatus
) {

    var expanded by remember { mutableStateOf(false) }

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
        if (expanded)
            Column(
                modifier = Modifier.padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                episodes.forEach { episode ->

                    EpisodeItem(
                        episode,
                        viewModel,
                        seasonStatus
                    )
                }
            }
    }
    Spacer(modifier = Modifier.height(12.dp))
}