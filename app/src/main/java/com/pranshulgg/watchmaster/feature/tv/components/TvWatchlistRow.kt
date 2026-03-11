package com.pranshulgg.watchmaster.feature.tv.components

import android.view.animation.RotateAnimation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.SeasonData
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.MaterialListShape
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.local.mapper.SeasonDataMapper
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvWatchlistRow(
    item: WatchlistItemEntity,
    index: Int,
    items: List<WatchlistItemEntity>,
    navController: NavController,
    onLongActionTvSeasonRequest: (SeasonEntity?, WatchlistItemEntity) -> Unit,
    seasons: List<SeasonEntity>,
) {
    val isOnly = items.singleOrNull() == item
    val isFirst = index == 0
    val isLast = index == items.lastIndex

    val shape = MaterialListShape(isOnly, isFirst, isLast)


    var expanded by rememberSaveable(item.id) { mutableStateOf(false) }

    val motionScheme = MaterialTheme.motionScheme

    Surface(
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .combinedClickable(
                onClick = {
                    expanded = !expanded
                },
                onLongClick = {
                    onLongActionTvSeasonRequest(null, item)
                }
            ),
        color = MaterialTheme.colorScheme.surfaceBright
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clipToBounds()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                PosterBox(
                    posterUrl = "https://image.tmdb.org/t/p/w154${item.posterPath}",
                    apiPath = item.posterPath,
                    cornerRadius = Radius.None
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.W900,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,

                        )
                    Text(
                        item.overview ?: "No overview found",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }


                val rotationAngle by animateFloatAsState(
                    targetValue = if (expanded) 180f else 0f,
                    animationSpec = motionScheme.slowEffectsSpec(),
                )

                Symbol(
                    R.drawable.keyboard_arrow_down_24px,
                    size = 32.dp,
                    modifier = Modifier.rotate(rotationAngle)
                )

            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = motionScheme.slowSpatialSpec()),
                exit = shrinkVertically(animationSpec = motionScheme.slowSpatialSpec()),
            ) {
                Column(
                    Modifier.padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer)
                    Spacer(Modifier.height(8.dp))
                    seasons.forEachIndexed { i, season ->
                        val isOnly = seasons.singleOrNull() == season
                        val isFirst = i == 0
                        val isLast = i == seasons.lastIndex

                        val shapeSeasonRow = MaterialListShape(isOnly, isFirst, isLast)

                        SeasonTvRow(
                            season,
                            shapeSeasonRow,
                            navController,
                            onLongActionTvSeasonRequest = {
                                onLongActionTvSeasonRequest(
                                    season,
                                    item
                                )
                            })
                    }
                }

            }
        }
    }


}