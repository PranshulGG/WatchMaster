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
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TonalToggleButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.ui.components.Tooltip
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol

data class MenuItemOptionList(
    val title: String,
    val leading: Int,
    val action: () -> Unit
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailFloatingToolBar(scrollBehavior: FloatingToolbarScrollBehavior) {
    val systemInsets = WindowInsets.systemBars.asPaddingValues()
    var expanded by remember { mutableStateOf(false) }

    val menuItemContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val menuItemContentTextStyle = MaterialTheme.typography.labelLarge

    val menuItemOptionList = listOf(
        MenuItemOptionList(title = "Pin", leading = R.drawable.keep_24px, action = {}),
        MenuItemOptionList(title = "Rate", leading = R.drawable.star_24px, action = {}),
        MenuItemOptionList(title = "Folder", leading = R.drawable.folder_24px, action = {}),
        MenuItemOptionList(title = "Share", leading = R.drawable.share_24px, action = {}),
        MenuItemOptionList(title = "Delete", leading = R.drawable.delete_24px, action = {})

    )

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

                    Button(
                        modifier = Modifier
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                        onClick = {}) {
                        Symbol(R.drawable.play_arrow_24px)
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Mark as watching")
                    }

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
}
