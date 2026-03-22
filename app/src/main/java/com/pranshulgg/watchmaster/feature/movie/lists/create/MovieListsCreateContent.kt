package com.pranshulgg.watchmaster.feature.movie.lists.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.SettingSection
import com.pranshulgg.watchmaster.core.ui.components.SettingTile
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MovieListsCreateContent(
    paddingValues: PaddingValues,
    listNameText: String,
    listDescriptionText: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddMovie: () -> Unit,
    onSave: () -> Unit,
    selectedMovieList: List<WatchlistItemEntity> = emptyList(),
) {


    val size = ButtonDefaults.MediumContainerHeight
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
            supportingText = "Give your list a short, memorable name"
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Gap(horizontal = ButtonDefaults.iconSpacingFor(ButtonDefaults.MinHeight))

                Text("Add movies", style = MaterialTheme.typography.labelLarge)
            }
        }


        if (selectedMovieList.isNotEmpty()) {
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
                color = if (listNameText.isNotEmpty()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.38f
                )
            )
            Gap(horizontal = ButtonDefaults.iconSpacingFor(size))
            Text("Save list", style = MaterialTheme.typography.titleMedium)
        }
        Gap(15.dp)
    }

}


@Composable
private fun Field(
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    title: String,
    supportingText: String
) {

    Column(
        modifier = Modifier.padding(horizontal = 18.dp)
    ) {
        Text(
            title,
            modifier = Modifier.padding(start = 5.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Gap(6.dp)
        OutlinedTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Radius.Large),
            onValueChange = { onValueChange(it) },
            singleLine = singleLine,
        )
        Gap(5.dp)
        Text(
            supportingText,
            modifier = Modifier.padding(start = 5.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

