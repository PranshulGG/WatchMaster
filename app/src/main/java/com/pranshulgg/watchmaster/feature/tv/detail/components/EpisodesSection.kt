package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.MaterialListShape
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@Composable
fun EpisodesSection(episodes: List<TvEpisodeEntity>, viewModel: TvDetailsViewModel) {
    MediaSectionCard(
        title = "Episodes",
        titleIcon = R.drawable.list_alt_24px,
        actionText = "Show episodes",
        actionOnClick = {},
        showAction = true
    ) {
        Column(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            episodes.forEachIndexed { index, episode ->

                EpisodeItem(
                    episode,
                    viewModel
                )

            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}