package com.pranshulgg.watchmaster.feature.tv.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.movie.components.WatchlistRow
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvItems(
    scrollBehavior: FloatingToolbarScrollBehavior,
    scrollBehaviorTopBar: TopAppBarScrollBehavior,
    navController: NavController,
    onLongActionTvRequest: (SeasonEntity, WatchlistItemEntity) -> Unit,
    pinnedItems: List<WatchlistItemEntity>,
    normalItems: List<WatchlistItemEntity>,
    viewModel: WatchlistViewModel,
    seasons: List<SeasonEntity>

) {

    val seasonsByShow = remember(seasons) {
        seasons.groupBy { it.showId }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior)
            .nestedScroll(scrollBehaviorTopBar.nestedScrollConnection)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        item {
            Spacer(Modifier.height(20.dp))
        }

        if (pinnedItems.isNotEmpty()) {
            item {
                Text(
                    text = "Pinned",
                    modifier = Modifier.padding(bottom = 5.dp, start = 3.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            itemsIndexed(
                pinnedItems,
                key = { _, item -> item.id }) { index, item ->

                val filteredSeasons = seasonsByShow[item.id].orEmpty()

                TvWatchlistRow(
                    item,
                    index,
                    pinnedItems,
                    navController,
                    onLongActionTvRequest,
                    viewModel,
                    filteredSeasons
                )
            }

            if (normalItems.isNotEmpty()) {
                item {
                    Text(
                        text = "Other",
                        modifier = Modifier.padding(bottom = 5.dp, start = 3.dp, top = 20.dp),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        itemsIndexed(normalItems, key = { _, item -> item.id }) { index, item ->

            val filteredSeasons = seasonsByShow[item.id].orEmpty()


            TvWatchlistRow(
                item,
                index,
                normalItems,
                navController,
                onLongActionTvRequest,
                viewModel,
                filteredSeasons
            )
        }


        item {
            Spacer(
                Modifier.height(
                    WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                            + ScreenOffset + 80.dp
                )
            )

        }
    }
}