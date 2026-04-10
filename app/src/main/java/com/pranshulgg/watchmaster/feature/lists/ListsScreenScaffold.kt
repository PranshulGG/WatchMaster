package com.pranshulgg.watchmaster.feature.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@Composable
fun ListsScreenScaffold(
    navController: NavController,
    viewModel: ListsViewModel,
    watchlistViewModel: WatchlistViewModel
) {

    val customLists by viewModel.customLists.collectAsStateWithLifecycle(initialValue = emptyList())
    val customListsLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = true)
    val watchlistItems by watchlistViewModel.watchlist.collectAsStateWithLifecycle()


    if (customListsLoading) {
        LoadingScreenPlaceholder()
        return
    }

    LargeTopBarScaffold(
        title = "Lists",
        navigationIcon = { NavigateUpBtn(navController) },
        fab = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(NavRoutes.listEntryScreen(-1L)) },
                text = { Text("Create list", style = MaterialTheme.typography.titleMedium) },
                icon = {
                    Symbol(
                        R.drawable.add_24px,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            if (customLists.isEmpty()) {
                EmptyContainerPlaceholder(
                    text = "No lists found",
                    description = "Create a list to get started",
                    icon = R.drawable.lists_24px
                )
            }
            ListsScreenContent(
                customLists,
                onClick = { navController.navigate(NavRoutes.viewListScreen(it)) },
                watchlistItems
            )
        }

    }


}