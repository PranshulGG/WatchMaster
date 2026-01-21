package com.pranshulgg.watchmaster.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun rememberScrollbarAlpha(scrollState: ScrollState): Float {
    if (scrollState.maxValue == 0) {
        return 0f
    }
    var visible by remember { mutableStateOf(false) }
    var hasShownIntro by remember { mutableStateOf(false) }
    var hasUserScrolled by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState.maxValue) {

        visible = true
        hasShownIntro = true
        delay(1200)
        if (!hasUserScrolled) {
            visible = false
        }
    }

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollState.isScrollInProgress) {
            hasUserScrolled = true
            visible = true
        } else if (hasUserScrolled) {
            delay(1000)
            visible = false
        }
    }

    return animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "ScrollbarAlpha"
    ).value
}

@Composable
fun Modifier.verticalColumnScrollbar(
    scrollState: ScrollState,
    alpha: Float,
    width: Dp = 3.dp,
    scrollBarColor: Color = MaterialTheme.colorScheme.primary,
    scrollBarCornerRadius: Float = 4f,
    endPadding: Float = 12f
): Modifier {
    if (alpha <= 0f) return this

    return drawWithContent {
        drawContent()

        val viewportHeight = size.height
        val totalContentHeight = scrollState.maxValue + viewportHeight
        val scrollBarHeight =
            (viewportHeight / totalContentHeight) * viewportHeight
        val scrollBarOffset =
            (scrollState.value / totalContentHeight) * viewportHeight

        drawRoundRect(
            color = scrollBarColor.copy(alpha = alpha),
            cornerRadius = CornerRadius(scrollBarCornerRadius),
            topLeft = Offset(size.width - endPadding, scrollBarOffset),
            size = Size(width.toPx(), scrollBarHeight)
        )
    }
}
