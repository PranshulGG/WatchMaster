package com.pranshulgg.watchmaster.screens.setting_pages.display

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.pranshulgg.watchmaster.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.utils.SelectableThemeColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerBtn() {

    val prefs = LocalAppPrefs.current
    var selectedColor = prefs.themeColor
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Surface(
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box(

            modifier = Modifier
                .width(24.dp)
                .height(36.dp)
                .background(
                    color = Color(prefs.themeColor.toColorInt())
                )
                .clickable(
                    onClick = {
                        scope.launch { sheetState.show() }
                    }
                ),
        ) {
        }
    }

    ActionBottomSheet(
        sheetState = sheetState,
        onCancel = {
            scope.launch { sheetState.hide() }
        },
        onConfirm = {
            prefs.setThemeColor(selectedColor)
            scope.launch { sheetState.hide() }
        }
    ) {
        SelectableThemeColors(onThemeColorChanged = { color ->
            selectedColor = color
        })

    }


}
