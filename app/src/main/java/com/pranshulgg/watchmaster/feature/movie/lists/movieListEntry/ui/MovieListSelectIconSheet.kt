package com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.data.local.mapper.toIcon
import com.pranshulgg.watchmaster.feature.movie.lists.MovieListsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieListSelectIconSheet(
    viewModel: MovieListsViewModel,
    sheetState: SheetState,
) {

    val uiState = viewModel.uiState.value
    val icons = MediaListsIcons.entries.toList()

    if (uiState.isSelectListIconSheetOpen) {

        var selectedIcon by remember { mutableStateOf(uiState.listIcon) }

        ActionBottomSheet(
            sheetState = sheetState,
            onConfirm = {
                viewModel.updateListIcon(selectedIcon)
                viewModel.hideSelectListIconSheet()
            },
            confirmBtnMaxWidth = true,
            onCancel = {
                viewModel.hideSelectListIconSheet()
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    icons.forEach { icon ->
                        val iconResource = icon.toIcon()

                        FilledTonalIconToggleButton(
                            checked = icon == selectedIcon,
                            modifier = Modifier.size(48.dp),
                            shapes = IconButtonDefaults.toggleableShapes(),
                            onCheckedChange = {
                                selectedIcon = icon
                            }
                        ) {
                            Symbol(
                                iconResource,
                                color = if (icon == selectedIcon) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                    }
                }
            }
        }
    }
}