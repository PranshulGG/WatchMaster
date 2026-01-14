package com.pranshulgg.watchmaster.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModelFactory())
) {
    val query = viewModel.query
    val results = viewModel.results
    val loading = viewModel.loading

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = { Text("Search TMDB") },
            singleLine = true
        )

        Button(
            onClick = { viewModel.search() },
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Text("Search")
        }

        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        LazyColumn {
            items(results) { item ->
                SearchRow(item)
            }
        }
    }
}

@Composable
fun SearchRow(item: SearchItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        val poster = item.posterPath?.let {
            "https://image.tmdb.org/t/p/w154$it"
        }

        AsyncImage(
            model = poster,
            contentDescription = item.title,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text(
                item.mediaType.uppercase(),
                style = MaterialTheme.typography.bodySmall
            )
            item.overview?.let {
                Text(it, maxLines = 2, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
