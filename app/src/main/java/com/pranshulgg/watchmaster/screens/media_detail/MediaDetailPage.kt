package com.pranshulgg.watchmaster.screens.media_detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.models.MovieDetailsViewModel
import com.pranshulgg.watchmaster.screens.media_detail.factory.MovieDetailsViewModelFactory

@Composable
fun MediaDetailPage(
    movieId: Long,
) {
    val viewModel: MovieDetailsViewModel = viewModel(
        factory = MovieDetailsViewModelFactory(LocalContext.current)
    )
    LaunchedEffect(movieId) {
        viewModel.load(movieId)
    }

    val movie = viewModel.state

    val loading = viewModel.loading

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    viewModel.state?.let {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            // Poster + Title
            Box {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${it.images.posters.firstOrNull()?.file_path}",
                    contentDescription = it.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.title,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Overview
            Text(
                text = it.overview ?: "No overview available",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Genres
            Text(
                text = "Genres",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                it.genres.forEach { genre ->
                    AssistChip(label = { Text(genre.name) }, onClick = {})
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cast
            Text(
                text = "Cast",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyRow {
                items(it.credits.cast) { castMember ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w200${castMember.profile_path}",
                            contentDescription = castMember.name,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                        Text(text = castMember.name, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = castMember.character ?: "",
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }
            }


            // Similarly: Reviews, Similar, Recommendations, Watch Providers…
        }
    } ?: run {
        Text(
            "Movie not found", modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}
