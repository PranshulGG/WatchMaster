package com.pranshulgg.watchmaster.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabScreen(
) {
    val prefs = LocalAppPrefs.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Text("cool")

        }

    }
}