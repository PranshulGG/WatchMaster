package com.pranshulgg.watchmaster.screens.movieTabs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.ui.components.PosterPlaceholder
import com.pranshulgg.watchmaster.ui.theme.LocalStatusColors
import com.pranshulgg.watchmaster.utils.formatDate
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WatchlistRow(
    item: WatchlistItemEntity,
    index: Int,
    items: List<WatchlistItemEntity>,
    navController: NavController
) {

    val isOnly = items.singleOrNull() == item
    val isFirst = index == 0
    val isLast = index == items.lastIndex
    val titleMaxLines = 2
    val overviewMaxLines = remember { mutableIntStateOf(1) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    val poster = item.posterPath?.let {
        "https://image.tmdb.org/t/p/w154$it"
    }

    val movieStatusLabel = when (item.status) {
        WatchStatus.WATCHING -> "Started • ${item.startedDate?.formatDate()}"
        WatchStatus.FINISHED -> "Finished • ${item.finishedDate?.formatDate()}"
        WatchStatus.INTERRUPTED -> "Interrupted • ${item.interruptedAt?.formatDate()}"
        else -> "Added • ${item.addedDate.formatDate()}"
    }

    val statusColor = LocalStatusColors.current

    val statusContainerColor = when (item.status) {
        WatchStatus.INTERRUPTED -> MaterialTheme.colorScheme.errorContainer
        WatchStatus.WATCHING -> statusColor.warning.bg
        WatchStatus.FINISHED -> statusColor.success.bg
        else -> statusColor.pending.bg
    }

    val statusContentColor = when (item.status) {
        WatchStatus.INTERRUPTED -> MaterialTheme.colorScheme.onErrorContainer
        WatchStatus.WATCHING -> statusColor.warning.on
        WatchStatus.FINISHED -> statusColor.success.on
        else -> statusColor.pending.on
    }


    var selectedMovieItem: WatchlistItemEntity? = null

    val shape = when {
        isOnly -> RoundedCornerShape(16.dp)
        isFirst -> RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 4.dp,
            bottomEnd = 4.dp
        )

        isLast -> RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )

        else -> RoundedCornerShape(4.dp)
    }

    if (isFirst) {
        Spacer(Modifier.height(20.dp))
    }

    Surface(
        shape = shape,
        modifier = Modifier
            .clip(shape)
            .combinedClickable(
                onClick = {
                    navController.navigate(NavRoutes.mediaDetail(item.id))
                },
                onLongClick = {
                    selectedMovieItem = item
                    scope.launch {
                        sheetState.show()
                    }
                }
            ),
        color = MaterialTheme.colorScheme.surfaceBright
    ) {
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
            Box(
                modifier = Modifier
                    .size(80.dp, height = 120.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!poster.isNullOrBlank()) {
                    val painter = rememberAsyncImagePainter(model = poster)

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop
                    )

                    if (painter.state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else {
                    PosterPlaceholder()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.W900,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp,
                    maxLines = titleMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult: TextLayoutResult ->
                        overviewMaxLines.value = if (textLayoutResult.lineCount == 1) 2 else 1
                    }
                )
                Text(
                    item.overview ?: "No overview found",
                    maxLines = overviewMaxLines.value,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
                Spacer(Modifier.height(5.dp))
                Surface(
                    color = statusContainerColor,
                    shape = CircleShape
                ) {

                    Text(
                        movieStatusLabel,
                        color = statusContentColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }

    if (isLast) {
        Spacer(
            Modifier.height(
                WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                        + ScreenOffset + 30.dp
            )
        )
    }



    ActionBottomSheet(
        showActions = false,
        sheetState = sheetState,
        onCancel = {
            scope.launch { sheetState.hide() }
        },
        onConfirm = {
            scope.launch { sheetState.hide() }
        }
    ) {

        if (selectedMovieItem != null) {
            WatchlistItemOptionsSheetContent(
                selectedMovieItem = selectedMovieItem,
                hideSheet = { scope.launch { sheetState.hide() } })
        }

    }

}