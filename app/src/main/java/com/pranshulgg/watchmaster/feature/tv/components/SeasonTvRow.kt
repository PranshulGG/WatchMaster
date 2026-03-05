package com.pranshulgg.watchmaster.feature.tv.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.feature.shared.media.components.WatchListStatusPill
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.asStatusDates
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.toWatchListItemStatusUiPill

@Composable
fun SeasonTvRow(seasonData: SeasonEntity, shape: Shape, navController: NavController) {

    val status = seasonData.status.toWatchListItemStatusUiPill(seasonData.asStatusDates())


    Surface(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(shape)
            .combinedClickable(
                onClick = {
                    navController.navigate(
                        NavRoutes.tvDetail(
                            seasonData.showId,
                            seasonData.seasonNumber
                        )
                    )
                },
            ),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clipToBounds()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PosterBox(
                    posterUrl = "https://image.tmdb.org/t/p/w154${seasonData.posterPath}",
                    apiPath = seasonData.posterPath,
                    cornerRadius = Radius.None,
                    height = 100.dp,
                    width = 65.dp
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = seasonData.name,
                        fontWeight = FontWeight.W900,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        "${seasonData.episodeCount} episodes",
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(5.dp))
                    WatchListStatusPill(
                        status.containerColor,
                        status.contentColor,
                        status.statusLabel,
                        seasonData.status,
                        seasonData.seasonUserRating
                    )
                }
//                SeasonProgress() // NOT READY YET
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SeasonProgress() {
    Box(contentAlignment = Alignment.Center) {
        CircularWavyProgressIndicator(
            progress = { 0.5f }
        )
        Text(
            "50%",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
