package com.pranshulgg.watchmaster.screens.media_detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.ui.components.DialogBasic
import com.pranshulgg.watchmaster.ui.components.RateMovieDialogContent
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UserNoteField(
    onNoteSave: (String) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        Text("Place holder text...")

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            TextButton(
                onClick = {
                    showDialog = true
                }, contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                shapes = ButtonDefaults.shapes()
            ) {
                Symbol(
                    R.drawable.edit_24px,
                    size = ButtonDefaults.IconSize,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Edit note", style = MaterialTheme.typography.labelLarge)
            }
        }
    }

    DialogBasic(
        show = showDialog,
        title = "Add a note",
        showDefaultActions = true,
        onDismiss = {
            showDialog = false
            note = ""
        },
        onConfirm = {
            onNoteSave(note)
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

