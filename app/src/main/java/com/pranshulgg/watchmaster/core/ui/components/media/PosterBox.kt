package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PosterBox(
    posterUrl: String?,
    width: Dp = 80.dp,
    height: Dp = 120.dp,
    fillMaxWidth: Boolean = false,
    cornerRadius: Dp = ShapeRadius.Medium,
    circular: Boolean = false,
    placeholder: @Composable () -> Unit = { PosterPlaceholder() },
    progressIndicatorSize: Dp = 24.dp,
    apiPath: String? // to check if the API provided a valid image before loading
) {
    Box(
        modifier = if (fillMaxWidth)
            Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(if (circular) ShapeRadius.Full else cornerRadius))
        else Modifier
            .size(width = width, height = height)
            .clip(RoundedCornerShape(if (circular) ShapeRadius.Full else cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        if (!apiPath.isNullOrBlank()) {
            val painter = rememberAsyncImagePainter(model = posterUrl)

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularWavyProgressIndicator(
                    modifier = Modifier.size(progressIndicatorSize),
                )
            } else if (painter.state is AsyncImagePainter.State.Error || painter.state is AsyncImagePainter.State.Empty) {
                placeholder()
            }
        } else {
            placeholder()
        }
    }
}
