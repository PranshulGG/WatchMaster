package com.pranshulgg.watchmaster.feature.movie.lists.movieListEntry

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieListEntryContent(
    paddingValues: PaddingValues,
    listNameText: String,
    listDescriptionText: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddMovie: () -> Unit,
    onSave: () -> Unit,
    selectedMovieList: List<WatchlistItemEntity> = emptyList(),
    onSelectIcon: () -> Unit,
    selectedListIcon: Int,
    isEditingList: Boolean = false,
//    editingItem: MovieListsEntity? = null,
//    watchlistMovies: List<WatchlistItemEntity>,
//    onUpdateMovieList: (List<WatchlistItemEntity>) -> Unit,
//    onUpdateSelectedIcon: (MediaListsIcons) -> Unit,
) {

    val size = ButtonDefaults.MediumContainerHeight
    val colorScheme = MaterialTheme.colorScheme


//
//    LaunchedEffect(moviesFiltered) {
//        if (editingItem != null) {
//            onNameChange(editingItem.name)
//            onDescriptionChange(editingItem.description)
//            onUpdateMovieList(moviesFiltered)
//            onUpdateSelectedIcon(editingItem.icon ?: MediaListsIcons.FOLDER)
//
//        }
//    }

    Column(
        Modifier.padding(
            top = paddingValues.calculateTopPadding() + 8.dp,
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {

        Field(
            value = listNameText,
            onValueChange = { onNameChange(it) },
            title = "Name",
            supportingText = "Give your list a short, memorable name",
            hasSelectIcon = true,
            selectedListIcon = selectedListIcon,
            onSelectIcon = {
                onSelectIcon()
            }
        )
        Gap(15.dp)
        Field(
            value = listDescriptionText,
            onValueChange = { onDescriptionChange(it) },
            title = "Description (optional)",
            supportingText = "Add a short description to explain what this list is about"
        )
        Gap(15.dp)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            OutlinedButton(
                onClick = {
                    onAddMovie()
                },
                shapes = ButtonDefaults.shapes(),
                contentPadding =
                    ButtonDefaults.contentPaddingFor(ButtonDefaults.MinHeight, hasStartIcon = true),
            ) {
                Symbol(
                    R.drawable.add_24px,
                    size = ButtonDefaults.iconSizeFor(ButtonDefaults.MinHeight),
                    color = colorScheme.onSurfaceVariant
                )
                Gap(horizontal = ButtonDefaults.iconSpacingFor(ButtonDefaults.MinHeight))

                Text("Add movies", style = MaterialTheme.typography.labelLarge)
            }
        }


        if (selectedMovieList.isNotEmpty()) {
            AddedMovieChips(selectedMovieList)
//        } else if (isEditingList && editingItem != null) {
//            AddedMovieChips(moviesFiltered)
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = onSave,
            enabled = listNameText.isNotEmpty(),
            modifier = Modifier
                .heightIn(size)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = ButtonDefaults.contentPaddingFor(size, hasStartIcon = true),
            shapes = ButtonDefaults.shapes(),
        ) {
            Symbol(
                R.drawable.check_24px,
                size = ButtonDefaults.iconSizeFor(size),
                color = if (listNameText.isNotEmpty()) colorScheme.onPrimary else colorScheme.onSurfaceVariant.copy(
                    alpha = 0.38f
                )
            )
            Gap(horizontal = ButtonDefaults.iconSpacingFor(size))
            Text(
                if (isEditingList) "Update list" else "Save list",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Gap(15.dp)
    }

}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun Field(
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    title: String,
    supportingText: String,
    hasSelectIcon: Boolean = false,
    selectedListIcon: Int = R.drawable.folder_24px,
    onSelectIcon: () -> Unit = {}
) {

    Column(
        modifier = Modifier.padding(horizontal = 18.dp),
    ) {
        if (!hasSelectIcon) {
            OutlinedTextField(
                value = value,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Radius.Large),
                onValueChange = { onValueChange(it) },
                placeholder = { Text(title) },
                singleLine = singleLine,
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {

                Tooltip(
                    tooltipText = "Select list icon",
                    preferredPosition = TooltipAnchorPosition.Below,

                    ) {
                    OutlinedIconButton(
                        onClick = {
                            onSelectIcon()
                        },
                        modifier = Modifier.size(56.dp),
                        shapes = IconButtonDefaults.shapes(),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Symbol(
                            selectedListIcon,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            size = 32.dp
                        )
                    }
                }

                Gap(horizontal = 12.dp)
                OutlinedTextField(
                    value = value,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(Radius.Large),
                    onValueChange = { onValueChange(it) },
                    placeholder = { Text(title) },
                    singleLine = singleLine,
                )
            }
        }
        Gap(5.dp)
        Text(
            supportingText,
            modifier = Modifier.padding(start = if (!hasSelectIcon) 5.dp else 73.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}


@Composable
private fun AddedMovieChips(selectedMovieList: List<WatchlistItemEntity>) {
    Gap(15.dp)
    Text(
        text = "Selected",
        modifier = Modifier.padding(bottom = 5.dp, top = 5.dp, start = 16.dp + 3.dp),
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.W700
    )

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        selectedMovieList.forEach { mov ->
            MediaChip(
                mov.title,
                containerColor = MaterialTheme.colorScheme.surfaceBright,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
