package com.pranshulgg.watchmaster.screens.media_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.repository.MovieRepository
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.model.WatchStatus
import com.pranshulgg.watchmaster.models.MovieDetailsViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModel
import com.pranshulgg.watchmaster.models.WatchlistViewModelFactory
import com.pranshulgg.watchmaster.network.TmdbApi
import com.pranshulgg.watchmaster.screens.media_detail.factory.MovieDetailsViewModelFactory
import com.pranshulgg.watchmaster.screens.media_detail.ui.CastItem
import com.pranshulgg.watchmaster.screens.media_detail.ui.MovieDetailActionsTop
import com.pranshulgg.watchmaster.screens.media_detail.ui.MovieDetailFloatingToolBar
import com.pranshulgg.watchmaster.screens.media_detail.ui.MovieHeroHeader
import com.pranshulgg.watchmaster.screens.media_detail.ui.SectionCard
import com.pranshulgg.watchmaster.ui.components.DialogBasic
import com.pranshulgg.watchmaster.ui.components.PosterBox
import com.pranshulgg.watchmaster.ui.components.RateMovieDialogContent
import com.pranshulgg.watchmaster.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.ui.theme.RobotoFlexWide
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun MediaDetailPage(
    movieId: Long,
    navController: NavController
) {
    val viewModel: MovieDetailsViewModel = viewModel(
        factory = MovieDetailsViewModelFactory(LocalContext.current)
    )
    LaunchedEffect(movieId) {
        viewModel.load(movieId)
    }

    val movie = viewModel.state

    val loading = viewModel.loading
    var minLoadingDone by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        minLoadingDone = true
    }

    val showLoading = loading || !minLoadingDone // loading to hide nav lag -hack

    val scrollBehavior =
        FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = Bottom)

    val scope = rememberCoroutineScope()


    val context = LocalContext.current
    val repository = remember {
        val db = WatchMasterDatabase.getInstance(context)
        WatchlistRepository(
            db.watchlistDao(),
            MovieRepository(
                api = TmdbApi.create(),
                dao = db.movieBundleDao()
            )
        )
    }

    val factory = remember {
        WatchlistViewModelFactory(repository)
    }

    val watchlistViewModel: WatchlistViewModel = viewModel(factory = factory)

    LaunchedEffect(movieId) {
        watchlistViewModel.observeItem(movieId)
    }

    val liveItem by watchlistViewModel.currentItem.collectAsStateWithLifecycle()
    var showRatingDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        bottomBar = {
            if (!showLoading)
                MovieDetailFloatingToolBar(
                    scrollBehavior,
                    movieId,
                    liveItem,
                    startWatching = { watchlistViewModel.start(movieId) },
                    resetWatching = { watchlistViewModel.reset(movieId) },
//                    finishWatching = { watchlistViewModel.finish(movieId) },
                    finishWatching = { showRatingDialog = true },
                    interruptWatching = { watchlistViewModel.interrupt(movieId) },
                )
        },
    ) { paddingValues ->
        if (showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    .zIndex(10f),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(modifier = Modifier.size(60.dp))
            }
        }
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
                MovieHeroHeader(
                    movie = it,
                    navController,
                    isFinished = liveItem?.status == WatchStatus.FINISHED,
                    userRating = liveItem?.userRating,
                    onUpdateRating = { newRating ->
                        watchlistViewModel.setUserRating(movieId, newRating)
                        scope.launch {
                            SnackbarManager.show("User rating updated")
                        }

                    }
                )
                MovieDetailActionsTop(status = liveItem?.status ?: WatchStatus.WATCHING)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            16.dp
                        )
                ) {


                    SectionCard(
                        title = "Overview",
                        titleIcon = R.drawable.overview_24px
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = it.overview,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SectionCard(
                        title = "Cast",
                        titleIcon = R.drawable.groups_2_24px
                    ) {
                        val director = it.credits.crew.firstOrNull { crew ->
                            crew.job == "Director"
                        }

                        val mainCast = it.credits.cast.take(8)


                        LazyRow {
                            director?.let { director ->
                                item {
                                    Spacer(Modifier.width(8.dp))
                                }
                                item {
                                    CastItem(
                                        character = "Director",
                                        name = director.name,
                                        profilePath = director.profile_path
                                    )

                                }
                                items(mainCast) { castMember ->
                                    CastItem(
                                        character = castMember.character ?: "",
                                        name = castMember.name,
                                        profilePath = castMember.profile_path
                                    )
                                }
                                item {
                                    Spacer(Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }
            }

        }

        DialogBasic(
            show = showRatingDialog,
            title = "Rate this movie",
            showDefaultActions = false,
            onDismiss = { showRatingDialog = false },
            content = {
                RateMovieDialogContent(
                    onCancel = { showRatingDialog = false },
                    onConfirm = { rating ->
                        watchlistViewModel.setUserRating(movieId, rating)
                        watchlistViewModel.finish(movieId)
                    }
                )
            }
        )
    }

}

