package com.pranshulgg.watchmaster.feature.movie.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.TooltipIconBtn
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity

@Composable
fun MovieListsContent(movieLists: List<MovieListsEntity>, onDelete: (Long) -> Unit) {

    LazyColumn() {
        items(
            movieLists,
            key = { item -> item.id }) { item ->

            ListItem(
                headlineContent = { Text(item.name) },
                supportingContent = {
                    if (item.description.isNotEmpty()) {
                        Text(item.description)
                    } else null
                },
                trailingContent = { Text(item.movieIds.toString()) },
                leadingContent = {
                    TooltipIconBtn(
                        onClick = { onDelete(item.id) },
                        icon = R.drawable.delete_24px,
                        tooltipText = "Delete"
                    )
                }
            )
        }
    }
}