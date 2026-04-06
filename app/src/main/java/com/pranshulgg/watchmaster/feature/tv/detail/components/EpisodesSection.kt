package com.pranshulgg.watchmaster.feature.tv.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.LoadingPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.data.local.entity.TvEpisodeEntity
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel
import com.pranshulgg.watchmaster.feature.tv.detail.ui.TvDetailsEpisodeInfoSheet
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EpisodesSection(
    episodes: List<TvEpisodeEntity>,
    viewModel: TvDetailsViewModel,
    seasonId: Long,
    seasonStatus: WatchStatus
) {

    var showSheet by remember { mutableStateOf(false) }
    var currentEp by remember { mutableStateOf<TvEpisodeEntity?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val carouselItemWidth = 512.dp
    val carouselState = rememberCarouselState(
        itemCount = { episodes.count() },
        initialItem = episodes.indexOfLast { it.isWatched }.coerceAtLeast(0)
    )
    val scope = rememberCoroutineScope()
    val watchedItems = episodes.count { it.isWatched }


    val watchedProgress = remember(episodes) {
        (watchedItems.toFloat() / episodes.size) * 100
    }

    LaunchedEffect(seasonStatus, episodes) {
        if (seasonStatus == WatchStatus.FINISHED && episodes.all { !it.isWatched }) {
            viewModel.markAllEpsWatched(seasonId)
        }
    }


    LaunchedEffect(watchedProgress) {
        if (episodes.isNotEmpty()) {
            viewModel.updateSeasonProgress(
                seasonId,
                watchedProgress.roundToInt()
            )
        }
    }


    MediaSectionCard(
        title = "Episodes",
        titleIcon = R.drawable.list_alt_24px,
        trailingContent = {
            MediaChip(
                "$watchedItems / ${episodes.size} watched",
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    ) {

        if (episodes.isEmpty()) {
            LoadingPlaceholder()
        }

        HorizontalMultiBrowseCarousel(
            state = carouselState,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(top = 10.dp, start = 16.dp, end = 16.dp),
            preferredItemWidth = carouselItemWidth,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(0.dp),
        ) { index ->


            val isUnlocked =
                when (index) {
                    0 -> episodes.getOrNull(1)?.isWatched == false
                    else -> episodes[index - 1].isWatched
                }

            val item = episodes[index]

            Box(
                modifier = Modifier
                    .maskClip(RoundedCornerShape(ShapeRadius.ExtraLarge))
                    .width(carouselItemWidth)
                    .height(130.dp)
            ) {
                EpisodeItem(
                    item,
                    onTrailingAction = {
                        currentEp = item
                        showSheet = true
                    },
                    carouselItemWidth,
                    onItemClick = {

                        if (carouselState.currentItem != index) {
                            scope.launch {
                                carouselState.animateScrollToItem(index)
                            }
                            return@EpisodeItem
                        }

                        if (!isUnlocked) {
                            SnackbarManager.show("Previous episode not watched")
                            return@EpisodeItem
                        }

                        if (seasonStatus == WatchStatus.WANT_TO_WATCH || seasonStatus == WatchStatus.FINISHED) {
                            SnackbarManager.show("Please mark the season as 'Watching' to track episodes")
                            return@EpisodeItem
                        }

                        if (item.isWatched) {
                            viewModel.markEpUnWatched(item.epId)
                        } else {
                            viewModel.markEpWatched(item.epId)
                        }
                    }
                )
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