package com.pranshulgg.watchmaster.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AppBarRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.FloatingToolbarHorizontalFabPosition
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.utils.Symbol

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController,
    scrollBehavior: FloatingToolbarScrollBehavior
) {
    val labelList = listOf("Home", "Movies", "TV series")
    val unSelectedIcons = listOf(
        R.drawable.home_24px,
        R.drawable.movie_24px,
        R.drawable.tv_24px,
    )
    val selectedIcons = listOf(
        R.drawable.home_filled_24px,
        R.drawable.movie_filled_24px,
        R.drawable.tv_filled_24px,
    )

    val colorScheme = MaterialTheme.colorScheme

    val systemInsets = WindowInsets.systemBars.asPaddingValues()


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

                    labelList.forEachIndexed { index, label ->
                        Tooltip(
                            label,
                            preferredPosition = TooltipAnchorPosition.Above,
                            spacing = 10.dp
                        ) {
                            ToggleButton(
                                modifier = Modifier.height(48.dp),
                                checked = selectedItem == index,
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
                                Crossfade(selectedItem == index) {
                                    if (it) Symbol(
                                        selectedIcons[index],
                                        color = colorScheme.onSurface
                                    ) else Symbol(
                                        unSelectedIcons[index],
                                        color = colorScheme.onPrimaryContainer
                                    )
                                }
                                AnimatedVisibility(
                                    visible = selectedItem == index,
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
                                        color = if (selectedItem == index) colorScheme.onSurface else colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(start = ButtonDefaults.IconSpacing)
                                    )
                                }
                            }
                        }
                    }
                    Tooltip(
                        if (selectedItem == 1) "Add movie" else "Add tv series",
                        preferredPosition = TooltipAnchorPosition.Above,
                        spacing = 10.dp
                    ) {
                        AnimatedVisibility(
                            modifier = Modifier.clip(CircleShape),
                            visible = selectedItem != 0,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            FilledIconButton(
                                modifier = Modifier.size(48.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                onClick = {
                                    navController.navigate(NavRoutes.search(if (selectedItem == 1) SearchType.MOVIE else SearchType.TV))
                                },
                            ) {
                                Symbol(
                                    R.drawable.add_24px,
                                    color = colorScheme.primaryContainer
                                )
                            }
                        }
                    }
                }

            },
        )
    }

}