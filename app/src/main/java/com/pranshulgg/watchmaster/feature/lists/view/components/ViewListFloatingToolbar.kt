package com.pranshulgg.watchmaster.feature.lists.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ViewListFloatingToolbar(
    selectedTab: Int,
    onItemSelected: (Int) -> Unit,
    scrollBehavior: FloatingToolbarScrollBehavior,
    onAction: (String) -> Unit
) {
    val labelList = listOf("Movies", "TV series")
    val unSelectedIcons = listOf(
        R.drawable.movie_24px,
        R.drawable.tv_24px,
    )
    val selectedIcons = listOf(
        R.drawable.movie_filled_24px,
        R.drawable.tv_filled_24px,
    )

    val colorScheme = MaterialTheme.colorScheme

    val systemInsets = WindowInsets.systemBars.asPaddingValues()

    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()

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
                    labelList.forEachIndexed { index, label ->
                        Tooltip(
                            label,
                            preferredPosition = TooltipAnchorPosition.Above,
                            spacing = 10.dp
                        ) {
                            ToggleButton(
                                modifier = Modifier.height(48.dp),
                                checked = selectedTab == index,
                                onCheckedChange = { onItemSelected(index) },
                                shapes = ToggleButtonDefaults.shapes(
                                    CircleShape,
                                    CircleShape,
                                    CircleShape
                                ),
                                colors = ToggleButtonDefaults.toggleButtonColors(
                                    containerColor = Color.Transparent,
                                    checkedContainerColor = colorScheme.surfaceContainer
                                ),

                                ) {
                                Crossfade(selectedTab == index) {
                                    if (it) Symbol(
                                        selectedIcons[index],
                                        color = colorScheme.onSurface
                                    ) else Symbol(
                                        unSelectedIcons[index],
                                        color = colorScheme.onPrimaryContainer
                                    )
                                }
                                AnimatedVisibility(
                                    visible = selectedTab == index,
                                    enter = expandHorizontally(motionScheme.defaultSpatialSpec()),
                                    exit = shrinkHorizontally(motionScheme.defaultSpatialSpec())
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        maxLines = 1,
                                        softWrap = false,
                                        overflow = TextOverflow.Clip,
                                        color = if (selectedTab == index) colorScheme.onSurface else colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(start = ButtonDefaults.IconSpacing)
                                    )
                                }
                            }
                        }
                    }

                    ListActions(
                        isExpanded = menuExpanded,
                        onExpand = { menuExpanded = it },
                        onAction = { onAction(it) }
                    )
                }
            },
        )
    }
}


@Composable
private fun ListActions(
    isExpanded: Boolean = false,
    onExpand: (Boolean) -> Unit,
    onAction: (String) -> Unit
) {

    val menuItemOptionList = listOf(
        Triple(R.drawable.edit_24px, "Edit", "EDIT_ACTION"),
        Triple(R.drawable.delete_24px, "Delete", "DELETE_ACTION"),
        Triple(R.drawable.keep_24px, "Pin", "PIN_ACTION")
    )
    val menuItemContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    val menuItemContentTextStyle = MaterialTheme.typography.labelLarge

    Box {
        FilledIconButton(
            modifier = Modifier.size(48.dp),
            onClick = { onExpand(true) },
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
            expanded = isExpanded,
            onDismissRequest = { onExpand(false) },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            shape = RoundedCornerShape(ShapeRadius.Large)
        ) {
            menuItemOptionList.forEach { option ->

                DropdownMenuItem(
                    modifier = Modifier.widthIn(min = 112.dp),
                    leadingIcon = {
                        Symbol(option.first, color = menuItemContentColor)
                    },
                    text = {
                        Text(
                            option.second,
                            color = menuItemContentColor,
                            style = menuItemContentTextStyle
                        )
                    },
                    onClick = {
                        onExpand(false)
                        onAction(option.third)
                    }
                )
            }
        }
    }
}