package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun CastItem(character: String, name: String, profilePath: String?, onCastClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(110.dp)
            .clickable(
                enabled = onCastClick != {},
                onClick = {
                    onCastClick()
                }
            )
    ) {
        PosterBox(
            posterUrl = "https://image.tmdb.org/t/p/w200${profilePath}",
            width = 80.dp,
            height = 80.dp,
            circular = true,
            apiPath = profilePath,
            placeholder = { ProfilePlaceholder(size = 80.dp) },
            progressIndicatorSize = 40.dp
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = name,
            modifier = Modifier.widthIn(max = 100.dp),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,

            )
        Text(
            text = character,
            modifier = Modifier.widthIn(max = 100.dp),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}