package com.pranshulgg.watchmaster.feature.shared.media.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.core.ui.components.media.RateMediaDialogContent
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.core.ui.theme.Radius

@Composable
fun DetailsNoteDialogContent(
    show: Boolean,
    note: String,
    initialNote: String,
    onNoteChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    DialogBasic(
        show = show,
        title = "Add a note",
        showDefaultActions = true,
        onDismiss = {
            onDismiss()
            onNoteChange(initialNote)
        },
        onConfirm = {
            onConfirm(note)
        },
        confirmText = "Save",
        content = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Radius.Large),
                value = note,
                onValueChange = onNoteChange,
                placeholder = { Text("Note...") }
            )
        }
    )
}

@Composable
fun DetailsRatingDialogContent(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    DialogBasic(
        show = show,
        title = "Rate this season",
        showDefaultActions = false,
        onDismiss = {
            onDismiss()
        },
        content = {
            RateMediaDialogContent(
                onCancel = {
                    onDismiss()
                },
                onConfirm = { rating ->
                    onConfirm(rating)
                }
            )
        }
    )
}


@Composable
fun DetailsConfirmationDialogContent(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    isTv: Boolean = false
) {

    val text = if (isTv) "season" else "movie"

    TextAlertDialog(
        show = show,
        title = "Delete $text",
        message = "Are you sure you want to delete this $text? this action cannot be undone",
        confirmText = "Confirm",
        onConfirm = {
            onConfirm()
        },
        onDismiss = {
            onDismiss()
        }
    )
}