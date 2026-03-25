package com.pranshulgg.watchmaster.feature.movie.lists.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn

@Composable
fun ViewMovieListScreen(navController: NavController, id: Long) {

    LargeTopBarScaffold(
        title = "$id",
        navigationIcon = { NavigateUpBtn(navController) }
    ) { pad ->
        Box(modifier = Modifier.padding(pad)) {
            Text("$id", style = MaterialTheme.typography.headlineLarge)
        }
    }

}