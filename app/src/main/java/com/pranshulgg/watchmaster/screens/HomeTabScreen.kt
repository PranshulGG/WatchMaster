package com.pranshulgg.watchmaster.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.helpers.NavRoutes
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.utils.Symbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabScreen(
    navController: NavController
) {
    val prefs = LocalAppPrefs.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nothing here yet")

                Button(onClick = {
                    navController.navigate(NavRoutes.mediaDetail(243875, false))
                }) {
                    Text("GO TO IDK TV")
                }
            }

        }

    }
}