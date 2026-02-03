package com.pranshulgg.watchmaster.screens.media_detail.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TonalToggleButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.models.MovieDetailsViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModelFactory
import com.pranshulgg.watchmaster.network.TmdbApi
import com.pranshulgg.watchmaster.screens.media_detail.factory.MovieDetailsViewModelFactory
import com.pranshulgg.watchmaster.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.ui.components.Tooltip
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol

data class MenuItemOptionList(
    val title: String,
    val leading: Int,
    val action: () -> Unit,
    val isInterruptOption: Boolean = false
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailFloatingToolBar(scrollBehavior: FloatingToolbarScrollBehavior, movieId: Long) {
    val systemInsets = WindowInsets.systemBars.asPaddingValues()
    var expanded by remember { mutableStateOf(false) }

    val menuItemContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val menuItemContentTextStyle = MaterialTheme.typography.labelLarge

    val menuItemOptionList = listOf(
        MenuItemOptionList(
            title = "Interrupt",
            leading = R.drawable.pause_24px,
            action = {}, isInterruptOption = true
        ),
        MenuItemOptionList(title = "Pin", leading = R.drawable.keep_24px, action = {}),
        MenuItemOptionList(title = "Rate", leading = R.drawable.star_24px, action = {}),
        MenuItemOptionList(title = "Folder", leading = R.drawable.folder_24px, action = {}),
        MenuItemOptionList(title = "Share", leading = R.drawable.share_24px, action = {}),
        MenuItemOptionList(title = "Delete", leading = R.drawable.delete_24px, action = {})
    )

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val repository = remember {
        val db = WatchMasterDatabase.getInstance(context)
        WatchlistRepository(
            db.watchlistDao(),
            MovieRepository(
                api = TmdbApi.create(),
                dao = db.movieBundleDao()
            )
        )
    }

    val factory = remember {
        WatchlistViewModelFactory(repository)
    }

    val watchlistViewModel: WatchlistViewModel = viewModel(factory = factory)

    LaunchedEffect(movieId) {
        watchlistViewModel.observeItem(movieId)
    }

    val liveItem by watchlistViewModel.currentItem.collectAsStateWithLifecycle()


    var displayItem by remember { mutableStateOf<WatchlistItemEntity?>(null) }

    LaunchedEffect(liveItem) {
        if (!showDialog) {
            displayItem = liveItem
        }
    }


    val dialogMessage = when (displayItem?.status) {
        WatchStatus.WATCHING -> "Are you sure you want to finish this movie?"
        WatchStatus.INTERRUPTED -> "Do you want to continue watching this movie?"
        WatchStatus.FINISHED -> "Reset this movie and add it back to your watchlist?"
        else -> "Are you sure you want to start watching this movie?"
    }


    Box(
        Modifier
            .fillMaxWidth(),
    ) {
        HorizontalFloatingToolbar(
            scrollBehavior = scrollBehavior,
            modifier = Modifier
                .padding(
                    top = ScreenOffset,
                    bottom = systemInsets.calculateBottomPadding()
                            + ScreenOffset
                )
                .align(Alignment.BottomCenter)
                .zIndex(1f),
            colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
            expanded = true,
            content = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    FloatingMainActionBtn(
                        onClick = { showDialog = true },
                        item = liveItem
                    )
                    Box {
                        FilledTonalIconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { expanded = true }
                        ) {
                            Symbol(
                                R.drawable.more_vert_24px,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(Radius.Large)
                        ) {
                            menuItemOptionList.forEach { option ->

                                if (option.isInterruptOption && liveItem?.status == WatchStatus.FINISHED) {
                                    return@forEach
                                }

                                DropdownMenuItem(
                                    leadingIcon = {
                                        Symbol(option.leading, color = menuItemContentColor)
                                    },
                                    text = {
                                        Text(
                                            option.title,
                                            color = menuItemContentColor,
                                            style = menuItemContentTextStyle
                                        )
                                    },
                                    onClick = {
                                        expanded = false
                                        option.action()
                                    }
                                )
                            }
                        }
                    }

                }
            },
        )
    }


    TextAlertDialog(
        show = showDialog,
        title = "Watch status",
        message = dialogMessage,
        confirmText = "Confirm",
        onConfirm = {
            when (liveItem?.status) {
                WatchStatus.WATCHING -> {
                    watchlistViewModel.finish(movieId)
                }

                WatchStatus.FINISHED -> {
                    watchlistViewModel.reset(movieId)
                }

                else -> {
                    watchlistViewModel.start(movieId)
                }
            }
        },
        onDismiss = {
            showDialog = false
        }
    )
}

@Composable
private fun FloatingMainActionBtn(onClick: () -> Unit, item: WatchlistItemEntity?) {


    val contentColor = when (item?.status) {
        WatchStatus.INTERRUPTED -> MaterialTheme.colorScheme.error
        WatchStatus.WATCHING -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }

    val containerColor = when (item?.status) {
        WatchStatus.INTERRUPTED -> MaterialTheme.colorScheme.onError
        WatchStatus.WATCHING -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceContainer
    }

    val btnLabels = when (item?.status) {
        WatchStatus.WATCHING -> "Mark as finished"
        WatchStatus.INTERRUPTED -> "Continue watching"
        WatchStatus.FINISHED -> "Reset"
        else -> "Mark as watching"
    }

    val btnIcons = when (item?.status) {
        WatchStatus.WATCHING -> R.drawable.check_24px
        WatchStatus.FINISHED -> R.drawable.restart_alt_24px
        else -> R.drawable.play_arrow_24px
    }

    Button(
        modifier = Modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        onClick = {
            onClick()
        }) {
        Symbol(
            btnIcons,
            color = contentColor
        )
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Text(
            btnLabels
        )
    }
}
