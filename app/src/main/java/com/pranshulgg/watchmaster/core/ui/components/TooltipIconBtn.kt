package com.pranshulgg.watchmaster.core.ui.components

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TooltipIconBtn(
    onClick: () -> Unit,
    icon: Int,
    tooltipText: String,
    tooltipPosition: TooltipAnchorPosition = TooltipAnchorPosition.Below,
    tooltipSpacing: Dp = 10.dp
) {
    Tooltip(
        tooltipText,
        preferredPosition = tooltipPosition,
        spacing = tooltipSpacing
    ) {
        IconButton(
            onClick = {
                onClick()
            },
            shapes = IconButtonDefaults.shapes()
        ) {
            Symbol(
                icon,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}
