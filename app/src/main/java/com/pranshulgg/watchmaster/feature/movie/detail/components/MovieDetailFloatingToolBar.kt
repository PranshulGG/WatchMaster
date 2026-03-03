package com.pranshulgg.watchmaster.feature.movie.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog

private data class MenuItemOptionList(
    val title: String,
    val leading: Int,
    val action: () -> Unit,
    val isInterruptOption: Boolean = false
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailFloatingToolBar(
    scrollBehavior: FloatingToolbarScrollBehavior,
    movieId: Long,
    liveItem: WatchlistItemEntity?,
    startWatching: () -> Unit,
    resetWatching: () -> Unit,
    finishWatching: () -> Unit,
    interruptWatching: () -> Unit,
    onDeleteMovie: () -> Unit,
    onMoviePin: () -> Unit,
    isPinned: Boolean = false,
) {
    val systemInsets = WindowInsets.systemBars.asPaddingValues()
    var expanded by remember { mutableStateOf(false) }

    val menuItemContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val menuItemContentTextStyle = MaterialTheme.typography.labelLarge

    val menuItemOptionList = listOf(
        MenuItemOptionList(
            title = "Interrupt",
            leading = R.drawable.pause_24px,
            action = { interruptWatching() }, isInterruptOption = true
        ),
        MenuItemOptionList(
            title = if (!isPinned) "Unpin" else "Pin",
            leading = R.drawable.keep_24px,
            action = { onMoviePin() }),
        MenuItemOptionList(title = "Folder", leading = R.drawable.folder_24px, action = {}),
        MenuItemOptionList(title = "Share", leading = R.drawable.share_24px, action = {}),
        MenuItemOptionList(
            title = "Delete",
            leading = R.drawable.delete_24px,
            action = { onDeleteMovie() })
    )

    var showDialog by remember { mutableStateOf(false) }
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
            expandedShadowElevation = 1.dp,
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
                        onClick = {
                            if (liveItem?.status == WatchStatus.WATCHING) {
                                finishWatching()
                            } else {
                                showDialog = true
                            }
                        },
                        item = liveItem
                    )
                    Box {
                        FilledIconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { expanded = true },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                        ) {
                            Symbol(
                                R.drawable.more_vert_24px,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(Radius.Large)
                        ) {
                            menuItemOptionList.forEach { option ->

                                if (option.isInterruptOption && liveItem?.status != WatchStatus.WATCHING) {
                                    return@forEach
                                }

                                DropdownMenuItem(
                                    modifier = Modifier.widthIn(min = if (option.isInterruptOption) 130.dp else 112.dp),
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
                    finishWatching()
                }

                WatchStatus.FINISHED -> {
                    resetWatching()
                }

                else -> {
                    startWatching()
                }
            }
        },
        onDismiss = {
            showDialog = false
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FloatingMainActionBtn(onClick: () -> Unit, item: WatchlistItemEntity?) {

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
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            onClick()
        },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
    ) {
        Symbol(
            btnIcons,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.width(ButtonDefaults.iconSpacingFor(48.dp)))
        Text(
            btnLabels,
            style = MaterialTheme.typography.titleMedium

        )
    }
}

