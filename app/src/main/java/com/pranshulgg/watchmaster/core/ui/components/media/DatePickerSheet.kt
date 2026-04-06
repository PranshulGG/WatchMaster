package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DatePickerSheet(
    show: Boolean,
    onDateSelected: (Long, Long?) -> Unit,
    initialDate: Long? = null,
    id: Long = -1L,
    onDismiss: () -> Unit
) {

    if (show && initialDate != null && id != -1L) {
        val datePickerState = rememberDatePickerState(initialDate)

        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = {
                onDismiss()
            },
            onConfirm = {
                onDateSelected(id, datePickerState.selectedDateMillis)
                onDismiss()
            }
        ) {
            DatePicker(
                state = datePickerState, colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                )
            )
        }
    }
}