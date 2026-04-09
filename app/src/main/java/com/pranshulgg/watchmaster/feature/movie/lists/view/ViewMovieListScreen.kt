package com.pranshulgg.watchmaster.feature.movie.lists.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.SettingSection
import com.pranshulgg.watchmaster.core.ui.components.SettingTile
import com.pranshulgg.watchmaster.core.ui.components.SettingsTileIcon
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.navigation.NavRoutes
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
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

    val isPinned = !(movieListEntity?.isPinned ?: false)

    var isConfirmationDialogOpen by remember { mutableStateOf(false) }

    LargeTopBarScaffold(
        title = movieListEntity?.name ?: "List",
        navigationIcon = { NavigateUpBtn(navController) },
//        subtitle = movieListEntity?.description,
        actions = {
            Actions(
                movieListEntity?.icon?.toIcon() ?: R.drawable.folder_24px,
                { navController.navigate(NavRoutes.movieListEntry(id)) },
                {
                    isConfirmationDialogOpen = true
                },
                { viewModel.setPinned(id, isPinned) },
                !isPinned
            )
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
        ) {
            ViewMovieListContent(movies, navController, movieListEntity?.description ?: "")
        }
    }


    TextAlertDialog(
        show = isConfirmationDialogOpen,
        onConfirm = {
            SnackbarManager.show("Deleted ${movieListEntity?.name ?: "list"}")
            viewModel.delete(id)
            navController.popBackStack()
        },
        onDismiss = { isConfirmationDialogOpen = false },
        title = "Delete ${movieListEntity?.name ?: "list"}",
        message = "Are you sure you want to delete this list? This can’t be undone"
    )
}


// Edit list action
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Actions(
    icon: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onPin: () -> Unit,
    isPinned: Boolean = false
) {

    data class Option(
        val title: String,
        val leading: Int,
        val action: () -> Unit,
    )

    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val options = listOf(
        Option("Edit", R.drawable.edit_24px) { onEdit() },
        Option("Delete", R.drawable.delete_24px) { onDelete() },
        Option(if (isPinned) "Unpin" else "Pin", R.drawable.keep_24px) { onPin() }
    )

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
            tooltipText = "More options",
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
                    isSheetOpen = true
                }, shapes = IconButtonDefaults.shapes()
            ) {
                Symbol(
                    R.drawable.more_vert_24px,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }


    if (isSheetOpen)
        ActionBottomSheet(
            sheetState,
            onConfirm = {},
            showActions = false,
            onCancel = { isSheetOpen = false }
        ) { hide ->
            SettingSection(
                isModalOption = true,
                tiles = options.map { op ->
                    SettingTile.ActionTile(
                        title = op.title,
                        leading = { SettingsTileIcon(op.leading) },
                        onClick = {
                            op.action()
                            hide()
                        }
                    )
                }
            )
        }
}