package com.pranshulgg.watchmaster.feature.shared.media.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.theme.Radius


@Composable
fun WatchListStatusPill(
    containerColor: Color,
    contentColor: Color,
    text: String,
    status: WatchStatus,
    rating: Double? = null
) {
    Row {
        Surface(
            color = containerColor,
            shape = if (status == WatchStatus.FINISHED) RoundedCornerShape(
                topStart = Radius.Full,
                bottomStart = Radius.Full
            ) else CircleShape
        ) {
            Text(
                text,
                color = contentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        if (status == WatchStatus.FINISHED) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topEnd = Radius.Small,
                    bottomEnd = Radius.Small
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 6.dp, end = 8.dp)
                ) {
                    Symbol(
                        R.drawable.star_24px,
                        size = 16.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        "${if (rating == 10.0) "10" else rating}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

