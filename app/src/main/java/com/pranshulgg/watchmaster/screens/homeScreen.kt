package com.pranshulgg.watchmaster.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.ui.components.SettingSection
import com.pranshulgg.watchmaster.ui.components.SettingTile

@Composable
fun HomeScreen(
) {
    val prefs = LocalAppPrefs.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
    ) { innerPadding ->


        Column(Modifier.padding(innerPadding)) {
            SettingSection(
                tiles = listOf(
                    SettingTile.ActionTile(
                        title = "Label text",
                        description = "Description text",
                        onClick = {}
                    ),
                    SettingTile.ActionTile(
                        title = "Label text",
                        description = "Description text",
                        onClick = {}
                    ),
                    SettingTile.SwitchTile(
                        title = "Use expressive",
                        checked = prefs.useExpressive,
                        onCheckedChange = prefs.setUseExpressive
                    ),
                    SettingTile.SwitchTile(
                        title = "Use dark theme",
                        checked = prefs.darkTheme,
                        onCheckedChange = prefs.setDarkTheme
                    )
                )
            )
        }

    }
}