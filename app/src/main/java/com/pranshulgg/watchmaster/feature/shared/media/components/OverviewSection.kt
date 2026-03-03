package com.pranshulgg.watchmaster.feature.shared.media.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard

@Composable
fun OverviewSection(text: String) {
    Spacer(Modifier.height(16.dp))
    MediaSectionCard(
        title = "Overview",
        titleIcon = R.drawable.overview_24px,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}