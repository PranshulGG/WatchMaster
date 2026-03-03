package com.pranshulgg.watchmaster.feature.tv.detail.components

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
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.buttonIcon
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.buttonLabel
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.confirmAction
import com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus.dialogMessage

private data class MenuItemOptionList(
    val title: String,
    val leading: Int,
    val action: () -> Unit,
    val isInterruptOption: Boolean = false
)

private data class UiState(
    val showDialog: Boolean = false,
    val expanded: Boolean = false
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TvDetailFloatingToolbar(
    scrollBehavior: FloatingToolbarScrollBehavior,
    id: Long,
    item: SeasonEntity,
    startWatching: () -> Unit,
    resetWatching: () -> Unit,
    finishWatching: () -> Unit,
    interruptWatching: () -> Unit,
    onDeleteSeason: () -> Unit,
    onPin: () -> Unit,
    isPinned: Boolean = false,
    isTv: Boolean
) {
    val systemInsets = WindowInsets.systemBars.asPaddingValues()
    val menuItemContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val menuItemContentTextStyle = MaterialTheme.typography.labelLarge
    var uiState by remember { mutableStateOf(UiState()) }

    val menuItemOptionList = listOf(
        MenuItemOptionList(
            "Interrupt",
            R.drawable.pause_24px,
            { interruptWatching() }, isInterruptOption = true
        ),
        MenuItemOptionList(
            if (!isPinned) "Unpin" else "Pin",
            R.drawable.keep_24px,
            { onPin() }),
        MenuItemOptionList("Folder", R.drawable.folder_24px, {}),
        MenuItemOptionList("Share", R.drawable.share_24px, {}),
        MenuItemOptionList(
            "Delete",
            R.drawable.delete_24px,
            { onDeleteSeason() })
    )

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
                            if (item.status == WatchStatus.WATCHING) {
                                finishWatching()
                            } else {
                                uiState = uiState.copy(showDialog = true)
                            }
                        },
                        item = item
                    )
                    Box {
                        FilledIconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { uiState = uiState.copy(expanded = true) },
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
                            expanded = uiState.expanded,
                            onDismissRequest = { uiState = uiState.copy(expanded = false) },
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(Radius.Large)
                        ) {
                            menuItemOptionList.forEach { option ->

                                if (option.isInterruptOption && item.status != WatchStatus.WATCHING) {
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
                                        uiState = uiState.copy(expanded = false)
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
        show = uiState.showDialog,
        title = "Watch status",
        message = item.status.dialogMessage(isTv = isTv),
        confirmText = "Confirm",
        onConfirm = {
            item.status.confirmAction(
                start = { startWatching() },
                reset = { resetWatching() },
                finish = { finishWatching() }
            )
        },
        onDismiss = {
            uiState = uiState.copy(showDialog = false)
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FloatingMainActionBtn(onClick: () -> Unit, item: SeasonEntity) {
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
            item.status.buttonIcon,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.width(ButtonDefaults.iconSpacingFor(48.dp)))
        Text(
            item.status.buttonLabel,
            style = MaterialTheme.typography.titleMedium

        )
    }
}

