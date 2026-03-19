package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.feature.movie.lists.ui.CreateMovieListSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListsScaffold(navController: NavController, viewModel: MovieListsViewModel) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LargeTopBarScaffold(
        title = "Movie lists",
        navigationIcon = { NavigateUpBtn(navController) },
        fab = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.showCreateListSheet() },
                text = { Text("Create list", style = MaterialTheme.typography.titleMedium) },
                icon = {
                    Symbol(
                        R.drawable.add_24px,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                })
        }
    ) { innerPadding ->

    }


    CreateMovieListSheet(viewModel, sheetState)
}