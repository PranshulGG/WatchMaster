package com.pranshulgg.watchmaster.screens.media_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.models.MovieDetailsViewModel
import com.pranshulgg.watchmaster.network.MovieBundleDto
import com.pranshulgg.watchmaster.screens.media_detail.factory.MovieDetailsViewModelFactory
import com.pranshulgg.watchmaster.screens.media_detail.ui.MovieDetailFloatingToolBar
import com.pranshulgg.watchmaster.screens.media_detail.ui.SectionCard
import com.pranshulgg.watchmaster.ui.components.PosterBox
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
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

    val scrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            MovieDetailFloatingToolBar(scrollBehavior, movieId)
        },
    ) { paddingValues ->

        viewModel.state?.let {
            Column(
                modifier = Modifier
                    .nestedScroll(scrollBehavior)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                    )
            )
            {
                if (loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    return@Column
                }

                MovieHeroHeader(movie = it)


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            16.dp
                        )
                ) {
//
//                    Card(
//                        shape = RoundedCornerShape(Radius.ExtraLarge)
//                    ) {
//                        Column(
//                            modifier = Modifier.padding(16.dp),
//                            verticalArrangement = Arrangement.spacedBy(6.dp)
//                        ) {
//                            Row(
//                                horizontalArrangement = Arrangement.spacedBy(5.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Symbol(
//                                    R.drawable.star_24px,
//                                    color = MaterialTheme.colorScheme.secondary
//                                )
//
//                                Text(
//                                    "Overview",
//                                    style = MaterialTheme.typography.titleMedium,
//                                    fontFamily = FontFamily(
//                                        Font(
//                                            R.font.roboto_flex,
//                                            variationSettings = FontVariation.Settings(
//                                                FontVariation.width(150f),
//                                                FontVariation.weight(1000)
//                                            )
//                                        )
//                                    ),
//                                    color = MaterialTheme.colorScheme.secondary
//                                )
//                            }
//                            Text(
//                                text = it.overview ?: "No overview available",
//                                style = MaterialTheme.typography.bodyLarge,
//                            )
//                        }
//                    }

                    SectionCard(
                        title = "Overview",
                        titleIcon = R.drawable.overview_24px
                    ) {
                        Text(
                            text = it.overview ?: "No overview available",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

//                Text(
//                    text = "Genres",
//                    style = MaterialTheme.typography.headlineSmall,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
//                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
//                    it.genres.forEach { genre ->
//                        AssistChip(label = { Text(genre.name) }, onClick = {})
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//


                    Text(
                        text = "Cast",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    val director = it.credits.crew.firstOrNull { crew ->
                        crew.job == "Director"
                    }
                    LazyRow {
                        director?.let { director ->
                            item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    PosterBox(
                                        posterUrl = "https://image.tmdb.org/t/p/w200${director.profile_path}",
                                        width = 100.dp,
                                        height = 160.dp
                                    )
                                    Text(
                                        text = director.name,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Director",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                        items(it.credits.cast) { castMember ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                PosterBox(
                                    posterUrl = "https://image.tmdb.org/t/p/w200${castMember.profile_path}",
                                    width = 100.dp,
                                    height = 160.dp
                                )
                                Text(
                                    text = castMember.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = castMember.character ?: "",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                }
            }

        } ?: run {
            Text(
                "Movie not found", modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }

}

@Composable
fun MovieHeroHeader(movie: MovieBundle) {
    Box(modifier = Modifier.fillMaxWidth()) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${movie.images.backdrops.firstOrNull()?.file_path}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.4f to MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
                            1.0f to MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 1f)
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.images.posters.firstOrNull()?.file_path}",
                contentDescription = movie.title,
                modifier = Modifier
                    .height(180.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(Radius.Large))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}
