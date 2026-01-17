package com.pranshulgg.watchmaster.ui.components


import androidx.compose.foundation.border
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R

@Composable
fun BottomNav(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val labelList = listOf("Home", "Movies", "TV series")
    val unSelectedIcons = listOf(
        R.drawable.home_24px,
        R.drawable.movie_24px,
        R.drawable.tv_24px,
    )
    val selectedIcons = listOf(
        R.drawable.home_filled_24px,
        R.drawable.movie_filled_24px,
        R.drawable.tv_filled_24px,
    )

    val colorScheme = MaterialTheme.colorScheme

    NavigationBar(
        modifier = Modifier.drawBehind {
            val strokeWidth = 1.dp.toPx()
            drawLine(
                color = colorScheme.outlineVariant,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = strokeWidth
            )
        }
    ) {
        labelList.forEachIndexed { index, labelList ->
            NavigationBarItem(
                onClick = {
                    onItemSelected(index)
                },
                selected = selectedItem == index,
                label = { Text(labelList, style = MaterialTheme.typography.labelLarge) },
                icon = {
                    Icon(
                        painter = painterResource(if (selectedItem == index) selectedIcons[index] else unSelectedIcons[index]),
                        contentDescription = null
                    )
                },
            )
        }
    }

}