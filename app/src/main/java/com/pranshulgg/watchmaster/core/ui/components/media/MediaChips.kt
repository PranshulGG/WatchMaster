package com.pranshulgg.watchmaster.core.ui.components.media

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.theme.Radius

@Composable
fun MediaChip(
    text: String,
    icon: Int? = null,
    contentColor: Color,
    containerColor: Color,
    shapeRadius: Dp = Radius.Full
) {

    Surface(
        color = containerColor,
        shape = RoundedCornerShape(shapeRadius)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = if (icon != null) 6.dp else 8.dp, end = 8.dp)
        ) {
            if (icon != null) {
                Symbol(icon, size = 16.dp, color = contentColor)
                Spacer(Modifier.width(3.dp))
            }
            Text(
                text,
                color = contentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}