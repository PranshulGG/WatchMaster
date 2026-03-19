package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController

data class MovieListsUiState(
    val isSheetOpen: Boolean = false,
    val listName: String = "",
    val listDescription: String = "",
)

@Composable
fun MovieListsScreen(navController: NavController) {

    val viewModel: MovieListsViewModel = hiltViewModel()

    MovieListsScaffold(navController, viewModel)
}