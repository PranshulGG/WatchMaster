package com.pranshulgg.watchmaster.feature.lists.listEntry

import android.app.ActionBar
import android.view.Display
import android.view.WindowInsetsAnimation
import android.view.WindowManager
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.GridConfigurationScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.IntSize.Companion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.Insets
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.Tooltip
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.components.media.PosterPlaceholder
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ListEntryContent(
    paddingValues: PaddingValues,
    listNameText: String,
    listDescriptionText: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    selectedMovieList: List<WatchlistItemEntity> = emptyList(),
    onSelectIcon: () -> Unit,
    selectedListIcon: Int,
) {


    Column(
        Modifier
            .padding(
                top = paddingValues.calculateTopPadding() + 8.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
            .verticalScroll(rememberScrollState())
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

        if (selectedMovieList.isNotEmpty()) {
            AddedMovieChips(selectedMovieList)
        }

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
                shape = RoundedCornerShape(ShapeRadius.Large),
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
                    shape = RoundedCornerShape(ShapeRadius.Large),
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


@OptIn(ExperimentalGridApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AddedMovieChips(selectedItems: List<WatchlistItemEntity>) {
    Gap(15.dp)

    MediaSectionCard(
        title = "Selected",
        titleIcon = R.drawable.lists_24px
    ) {

        FlowRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            selectedItems.forEach { item ->


                Surface(
                    shape = RoundedCornerShape(ShapeRadius.Full),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                top = 6.dp,
                                bottom = 6.dp,
                                start = 6.dp,
                                end = 10.dp
                            )
                            .fillMaxWidth(fraction = 0.462f)
                    ) {
                        PosterBox(
                            posterUrl = "https://image.tmdb.org/t/p/original${item.posterPath}",
                            apiPath = item.posterPath,
                            cornerRadius = ShapeRadius.Full,
                            width = 30.dp,
                            height = 30.dp,
                            placeholder = { PosterPlaceholder(size = 0.2f, hasPadding = false) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            item.title,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
