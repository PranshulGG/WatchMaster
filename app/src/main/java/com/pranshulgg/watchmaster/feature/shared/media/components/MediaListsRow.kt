package com.pranshulgg.watchmaster.feature.shared.media.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.core.ui.components.Gap

@Composable
fun MediaListsRow(
    headline: String,
    description: String = "null",
    leading: @Composable (() -> Unit)? = null,
    shapes: RoundedCornerShape,
    onClick: () -> Unit,
    trailing: @Composable (() -> Unit)? = null,
    media: @Composable (() -> Unit)? = null
) {
    Surface(
        shape = shapes,
        color = MaterialTheme.colorScheme.surfaceBright,
        modifier = Modifier
            .fillMaxWidth()
    ) {
//        Row(
//            modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp),
//            verticalAlignment = if (description == "" && media == null) Alignment.CenterVertically else Alignment.CenterVertically,
//        ) {
//            if (leading != null) {
//                leading()
//                Gap(horizontal = 12.dp)
//            }
//
//            Column(
//            ) {
//                Text(
//                    headline,
//                    fontSize = 17.sp,
//                    fontWeight = FontWeight.W900,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//                if (description != "") {
//                    Text(
//                        description,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//
//
//                if (media != null) {
//                    media()
//                }
//
//            }
//            if (trailing != null) {
//                Spacer(Modifier.weight(1f))
//                Gap(horizontal = 12.dp)
//                trailing()
//            }
//
//        }
        ListItem(
            modifier = Modifier.clickable(
                onClick = { onClick() }
            ),
            leadingContent = leading,
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceBright
            ),
            headlineContent = {
                Text(
                    headline,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W900,
                    fontSize = 17.sp,
                )
            },
            supportingContent = {
                Column {
                    if (description != "") {
                        Text(
                            description,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (media != null) {
                        media()
                    }
                }
            },
            trailingContent = trailing
        )
    }
}