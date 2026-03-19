package com.pranshulgg.watchmaster.feature.movie.lists.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.feature.movie.lists.MovieListsViewModel
import com.pranshulgg.watchmaster.feature.movie.lists.components.CreateMovieListSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMovieListSheet(viewModel: MovieListsViewModel, sheetState: SheetState) {

    val uiState = viewModel.uiState.value

    if (uiState.isSheetOpen)
        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = { viewModel.hideCreateListSheet() },
            onConfirm = {},
            confirmText = "Create"
        ) {
            CreateMovieListSheetContent(
                listNameText = uiState.listName,
                listDescriptionText = uiState.listDescription,
                onNameChange = { viewModel.updateListName(it) },
                onDescriptionChange = { viewModel.updateListDescription(it) }
            )
        }

}