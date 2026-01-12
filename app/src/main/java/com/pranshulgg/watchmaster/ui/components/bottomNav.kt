package com.pranshulgg.watchmaster.ui.components


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
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


    NavigationBar(
    ) {
        labelList.forEachIndexed { index, labelList ->
            NavigationBarItem(
                onClick = {
                    onItemSelected(index)
                },
                selected = selectedItem == index,
                label = { Text(labelList, fontSize = 14.sp) },
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