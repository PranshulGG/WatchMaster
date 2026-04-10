package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PosterPlaceholder(
    bgColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    size: Float = 1f,
    hasPadding: Boolean = true
) {

    val sizeIcon = 40.dp * size
    val spacing = 6.dp * size

    Surface(
        color = bgColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (hasPadding) 10.dp else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(sizeIcon),
                shape = MaterialShapes.Arrow.toShape(),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            ) { }
            Row {
                Surface(
                    modifier = Modifier.size(sizeIcon),
                    shape = MaterialShapes.VerySunny.toShape(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                ) { }
                Spacer(Modifier.width(spacing))
                Surface(
                    modifier = Modifier.size(sizeIcon),
                    shape = MaterialShapes.Pill.toShape(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                ) { }
            }
        }
    }

}

