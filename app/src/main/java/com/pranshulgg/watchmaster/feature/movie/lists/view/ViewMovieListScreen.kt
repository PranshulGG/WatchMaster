package com.pranshulgg.watchmaster.feature.movie.lists.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.AvatarIcon
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
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
            AvatarIcon(icon = movieListEntity?.icon?.toIcon() ?: R.drawable.folder_24px)
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
        ) {
//            Text("$id", style = MaterialTheme.typography.headlineLarge)

            Column() {
                movies.forEach { mov -> Text(mov.title) }

            }
        }
    }

}