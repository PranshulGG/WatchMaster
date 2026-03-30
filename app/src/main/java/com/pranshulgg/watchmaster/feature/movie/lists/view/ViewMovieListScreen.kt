package com.pranshulgg.watchmaster.feature.movie.lists.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.AvatarIcon
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.data.local.mapper.toIcon
import com.pranshulgg.watchmaster.feature.movie.lists.MovieListsViewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel

@Composable
fun ViewMovieListScreen(navController: NavController, id: Long) {

    val viewModel: MovieListsViewModel = hiltViewModel()
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()

    val watchlistItems by watchlistViewModel.watchlist.collectAsStateWithLifecycle()
    val movieList by viewModel.movieLists.collectAsStateWithLifecycle(initialValue = emptyList())

    val movieListEntity = movieList.find { it.id == id }

    val movies =
        watchlistItems.filter { it.mediaType == "movie" && movieListEntity?.movieIds?.contains(it.id) == true }

    LargeTopBarScaffold(
        title = movieListEntity?.name ?: "List",
        navigationIcon = { NavigateUpBtn(navController) },
        subtitle = movieListEntity?.description,
        actions = {
            Actions(movieListEntity?.icon?.toIcon() ?: R.drawable.folder_24px, onClick = {
                navController.navigate(NavRoutes.movieListEntry(id))
            })
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
        ) {

            Column() {
                movies.forEach { mov -> Text(mov.title) }

            }
        }
    }
}


// Edit list action
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun Actions(icon: Int, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 8.dp)
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.tertiaryContainer, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Symbol(
                icon,
                size = 24.dp,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        Spacer(Modifier.width(5.dp))

        Tooltip(
            tooltipText = "Edit list",
            spacing = 10.dp,
            preferredPosition = TooltipAnchorPosition.Below
        ) {
            OutlinedIconButton(
                modifier = Modifier.width(52.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                ),
                onClick = {
                    onClick()
                }, shapes = IconButtonDefaults.shapes()
            ) {
                Symbol(
                    R.drawable.edit_24px,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }
}