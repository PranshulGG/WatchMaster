package com.pranshulgg.watchmaster.feature.movie.lists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.core.ui.theme.Radius

@Composable
fun CreateMovieListSheetContent(
    listNameText: String,
    listDescriptionText: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = listNameText,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Radius.Large),
            onValueChange = { onNameChange(it) },
            singleLine = true,
            label = { Text("Name") }
        )

        OutlinedTextField(
            value = listDescriptionText,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Radius.Large),
            onValueChange = { onDescriptionChange(it) },
            label = { Text("Description") }
        )
    }

}