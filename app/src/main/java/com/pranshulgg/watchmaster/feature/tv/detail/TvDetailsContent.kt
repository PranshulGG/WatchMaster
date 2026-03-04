package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.feature.shared.media.components.CastTvSection
import com.pranshulgg.watchmaster.feature.shared.media.components.NotesSection
import com.pranshulgg.watchmaster.feature.shared.media.components.OverviewSection
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvHeroHeader

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvDetailsContent(
    tvItem: TvBundle,
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior,
    seasonsData: List<SeasonEntity>,
    seasonNumber: Int,
    season: SeasonEntity?,
    noteAction: () -> Unit
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
                    navController,
                    isFinished = seasonsData.find { it.seasonNumber == seasonNumber }?.status == WatchStatus.FINISHED,
                    season,
                )
                MediaStatusSection(status = season.status)
                OverviewSection(tvItem.overview)
                NotesSection(
                    season.seasonNotes.isNullOrBlank(),
                    {
                        noteAction()
                    },
                    season.seasonNotes ?: ""
                )
                CastTvSection(tvItem)
            } else {
                Text("No season found")
            }
        }
    }

}