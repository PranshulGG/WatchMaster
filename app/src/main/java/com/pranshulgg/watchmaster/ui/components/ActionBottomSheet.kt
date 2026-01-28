package com.pranshulgg.watchmaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ActionBottomSheet(
    sheetState: SheetState,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "Save",
    cancelText: String = "Cancel",
    showActions: Boolean = true,

    content: @Composable ColumnScope.() -> Unit,
) {
    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onCancel,
            scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
            dragHandle = {
                Box(
                    Modifier
                        .padding(top = 22.dp, bottom = 12.dp)
                        .height(4.dp)
                        .width(32.dp)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant, shape = CircleShape)
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
            ) {

                content()

                Spacer(Modifier.height(12.dp))
                if (showActions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, start = 16.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            modifier = Modifier.defaultMinSize(minWidth = 90.dp, minHeight = 45.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            onClick = {
                                onCancel()
                            }, shapes = ButtonDefaults.shapes()
                        ) {
                            Text(
                                cancelText,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 16.sp
                            )
                        }
                        Button(
                            onClick = {
                                onConfirm()
                            },

                            shapes = ButtonDefaults.shapes(),
                            modifier = Modifier.defaultMinSize(minWidth = 90.dp, minHeight = 45.dp),
                        ) {
                            Text(confirmText, fontSize = 16.sp)
                        }

                    }
                }

            }
        }
    }
}
