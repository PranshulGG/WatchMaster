package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProfilePlaceholder(
    size: Dp = 200.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    val headSize = size * 0.4f
    val headOffsetY = -size * 0.15f
    val bodyOffsetY = size * 0.6f

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .size(headSize)
                .align(Alignment.Center)
                .offset(y = headOffsetY)
                .clip(MaterialShapes.Cookie7Sided.toShape())
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )

        Box(
            modifier = Modifier
                .size(size)
                .align(Alignment.BottomCenter)
                .offset(y = bodyOffsetY)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )
    }
}
