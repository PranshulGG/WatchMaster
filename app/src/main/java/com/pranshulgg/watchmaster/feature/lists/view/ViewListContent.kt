package com.pranshulgg.watchmaster.feature.lists.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.feature.shared.media.components.MovieWatchlistRow
import com.pranshulgg.watchmaster.feature.shared.media.components.tv.TvWatchlistRow

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ViewListContent(
    movies: List<WatchlistItemEntity>,
    tv: List<WatchlistItemEntity>,
    seasonItems: List<SeasonEntity>,
    navController: NavController,
    description: String,
    toolBarScrollBehavior: FloatingToolbarScrollBehavior,
    selectedTab: Int
) {


    val seasonsByShow = remember(seasonItems) {
        seasonItems.groupBy { it.showId }
    }

    if ((selectedTab == 0 && movies.isEmpty()) || (selectedTab == 1 && tv.isEmpty())) {
        EmptyContainerPlaceholder(
            text = if (selectedTab == 0) "No movies found" else "No series found",
            description = "Add ${if (selectedTab == 0) "movies" else "series"} to this list to get started",
            icon = R.drawable.lists_24px
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .nestedScroll(toolBarScrollBehavior),
    ) {
        item {
            if (description.isNotBlank()) {
                MediaSectionCard(
                    title = "Description",
                    titleIcon = R.drawable.description_24px,
                    noPadding = true
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = description,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Gap(12.dp)
            }
        }

        if (selectedTab == 0) {
            itemsIndexed(movies, key = ({ _, movie -> movie.id })) { index, mov ->
                MovieWatchlistRow(
                    mov,
                    index,
                    movies,
                    navController,
                    onLongActionMovieRequest = {}
                )
                Gap(2.dp)
            }
        }

        if (selectedTab == 1) {

            itemsIndexed(tv, key = ({ _, tv -> tv.id })) { index, tvItem ->

                val filteredSeasons = seasonsByShow[tvItem.id].orEmpty()

                TvWatchlistRow(
                    tvItem,
                    index,
                    tv,
                    navController,
                    onLongActionTvSeasonRequest = { _, _ -> },
                    seasons = filteredSeasons
                )
                Gap(2.dp)
            }

        }

        item {
            Spacer(
                Modifier.height(
                    WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() + 16.dp
                )
            )
        }
    }
}