package com.pranshulgg.watchmaster.screens

import android.os.Build
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.utils.NavigateUpBtn
import com.pranshulgg.watchmaster.utils.Symbol
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.screens.setting_pages.display.ColorPickerBtn
import com.pranshulgg.watchmaster.ui.components.SettingSection
import com.pranshulgg.watchmaster.ui.components.SettingTile
import com.pranshulgg.watchmaster.ui.components.SettingsTileIcon

@Composable
fun SettingsPage(navController: NavController) {

    val prefs = LocalAppPrefs.current
    val isAndroid12Plus = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    LargeTopBarScaffold(
        title = "Settings",
        navigationIcon = { NavigateUpBtn(navController) },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
        ) {
            SettingSection(
                title = "App looks",
                tiles = listOf(
                    SettingTile.DialogOptionTile(
                        leading = { SettingsTileIcon(R.drawable.palette_24px) },
                        title = "App theme",
                        options = listOf("Dark", "Light", "System"),
                        selectedOption = prefs.appTheme,
                        onOptionSelected = {
                            prefs.setAppTheme(it)
                        }
                    ),
                    SettingTile.SwitchTile(
                        leading = {
                            if (prefs.isCustomTheme) ColorPickerBtn() else SettingsTileIcon(
                                R.drawable.colorize_24px
                            )
                        },
                        title = "Use custom color",
                        description = "Select a seed color to generate the theme",
                        checked = prefs.isCustomTheme,
                        onCheckedChange = { checked ->
                            prefs.useCustomTheme(checked)
                        }
                    ),
                    SettingTile.SwitchTile(
                        leading = {
                            SettingsTileIcon(
                                R.drawable.wallpaper_24px
                            )
                        },
                        title = "Dynamic colors",
                        description = "Use wallpaper colors",
                        checked = useDynamicColor,
                        onCheckedChange = { checked ->

                        }
                    ),
                )
            )

        }
    }
}