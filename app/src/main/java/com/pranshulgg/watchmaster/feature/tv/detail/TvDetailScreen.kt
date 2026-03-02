package com.pranshulgg.watchmaster.feature.tv.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.media.CastItem
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.components.media.MediaStatusSection
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailsViewModel
import com.pranshulgg.watchmaster.feature.movie.detail.components.MovieHeroHeader
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.detail.components.TvHeroHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvDetailsScreen(id: Long, seasonNumber: Int, navController: NavController) {
    val viewModel: TvDetailsViewModel = hiltViewModel()

    LaunchedEffect(id) {
        viewModel.load(id, seasonNumber)
    }


    val loading = viewModel.loading
    var minLoadingDone by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        minLoadingDone = true
    }


    val watchlistViewModel: WatchlistViewModel = hiltViewModel()


    val seasonsData by watchlistViewModel
        .seasonsForShow(id)
        .collectAsState(initial = emptyList())

    val showLoading = loading || !minLoadingDone // loading to hide nav lag -hack

    val scope = rememberCoroutineScope()

    val season = seasonsData.find { it.seasonNumber == seasonNumber }


    val existingNoteContent = season?.seasonNotes;
    var showNoteDialog by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }

    note = existingNoteContent ?: ""

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    )
    { paddingValues ->
        if (showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    .zIndex(10f),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(modifier = Modifier.size(60.dp))
                Box(Modifier.padding(bottom = paddingValues.calculateBottomPadding()))
            }
        }


        viewModel.state?.let { tvItem ->


            LazyColumn(
                modifier = Modifier
                    .imePadding()
            ) {
                item {
                    if (season != null) {
                        TvHeroHeader(
                            tvItem,
                            navController,
                            isFinished = seasonsData.find { it.seasonNumber == seasonNumber }?.status == WatchStatus.FINISHED,
                            season,
                        )

                        MediaStatusSection(status = season.status ?: WatchStatus.WATCHING)
                        Spacer(Modifier.height(16.dp))
                        MediaSectionCard(
                            title = "Overview",
                            titleIcon = R.drawable.overview_24px,
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = tvItem.overview,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        MediaSectionCard(
                            title = "Notes",
                            titleIcon = R.drawable.sticky_note_2_24px,
                            showAction = true,
                            actionOnClick = {
                                showNoteDialog = true
                            },
                            actionText = "Edit note"
                        ) {

                            if (!existingNoteContent.isNullOrBlank()) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        shape = RoundedCornerShape(Radius.Medium),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(min = 64.dp)
                                    ) {
                                        Text(
                                            existingNoteContent,
                                            modifier = Modifier.padding(10.dp),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                            }


                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        MediaSectionCard(
                            title = "Cast",
                            titleIcon = R.drawable.groups_2_24px,
                        ) {
                            val mainCast = tvItem.credits.cast
                            LazyRow {
                                item {
                                    Spacer(Modifier.width(8.dp))
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
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                        )
                    } else {
                        Text("No season found")
                    }
                }
            }
        }
    }


    DialogBasic(
        show = showNoteDialog,
        title = "Add a note",
        showDefaultActions = true,
        onDismiss = {
            showNoteDialog = false
            note = existingNoteContent ?: ""
        },
        onConfirm = {
            watchlistViewModel.setSeasonNote(id, note)
        },
        confirmText = "Save",
        content = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Radius.Large),
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("Note...") }
            )
        }
    )

}
