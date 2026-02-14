package com.pranshulgg.watchmaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.size.Size
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.utils.Symbol


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PosterPlaceholder(
    bgColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    iconSize: Dp = 40.dp
) {
    Surface(
        color = bgColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(iconSize),
                shape = MaterialShapes.Arrow.toShape(),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            ) { }
            Row {
                Surface(
                    modifier = Modifier.size(iconSize),
                    shape = MaterialShapes.VerySunny.toShape(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                ) { }
                Spacer(Modifier.width(5.dp))
                Surface(
                    modifier = Modifier.size(iconSize),
                    shape = MaterialShapes.Pill.toShape(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                ) { }
            }
        }
    }

}

