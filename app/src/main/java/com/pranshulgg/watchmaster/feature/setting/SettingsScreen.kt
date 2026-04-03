package com.pranshulgg.watchmaster.feature.setting

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.prefs.LocalAppPrefs
import com.pranshulgg.watchmaster.core.ui.components.CheckboxRow
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.SettingSection
import com.pranshulgg.watchmaster.core.ui.components.SettingTile
import com.pranshulgg.watchmaster.core.ui.components.SettingsTileIcon
import com.pranshulgg.watchmaster.core.ui.components.TextAlertDialog
import com.pranshulgg.watchmaster.feature.setting.components.ColorPickerBtn

@Composable
fun SettingsScreen(navController: NavController) {

    val prefs = LocalAppPrefs.current
    val isAndroid12Plus = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val context = LocalContext.current


    var exportWatchlistChecked by remember { mutableStateOf(false) }
    var exportMovieListChecked by remember { mutableStateOf(false) }

    val exportLauncher = exportLauncher(context, exportWatchlistChecked, exportMovieListChecked)
    val importLauncher = importLauncher(context)

    var isExportDialogOpen by remember { mutableStateOf(false) }


    var isWarningImportDialogOpen by remember { mutableStateOf(false) }

    LargeTopBarScaffold(
        title = "Settings",
        navigationIcon = { NavigateUpBtn(navController) },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SettingSection(
                title = "App looks",
                tiles = listOf(
                    SettingTile.DialogOptionTile(
                        leading = { SettingsTileIcon(R.drawable.palette_24px) },
                        title = "App theme",
                        options = listOf(
                            "Dark",
                            "Light",
                            "System",
                        ),
                        selectedOption = prefs.appTheme,
                        onOptionSelected = {
                            prefs.setAppTheme(it)
                        }
                    ),
                    SettingTile.SwitchTile(
                        leading = {
                            if (prefs.isCustomTheme) ColorPickerBtn() else SettingsTileIcon(
                                R.drawable.brush_24px
                            )
                        },
                        title = "Use custom color",
                        description = "Select a seed color to generate the theme",
                        checked = prefs.isCustomTheme,
                        enabled = !prefs.useDynamicColor,
                        onCheckedChange = { checked ->
                            prefs.useCustomTheme(checked)
                            if (!checked) {
                                prefs.setThemeColor("#2196f3")
                            }
                        }
                    ),
                    SettingTile.SwitchTile(
                        leading = {
                            SettingsTileIcon(
                                R.drawable.photo_24px
                            )
                        },
                        title = "Dynamic colors",
                        description = "Use wallpaper colors",
                        checked = prefs.useDynamicColor,
                        enabled = isAndroid12Plus && !prefs.isCustomTheme,
                        onCheckedChange = { checked ->
                            prefs.setDynamicColor(checked)
                        }
                    ),
                ),
            )
            SettingSection(
                title = "General",
                tiles = listOf(
                    SettingTile.DialogOptionTile(
                        leading = { SettingsTileIcon(R.drawable.home_filled_24px) },
                        title = "Starting default screen",
                        options = listOf(
                            "Home",
                            "Movies",
                            "TV series",
                        ),
                        selectedOption = prefs.defaultTab,
                        onOptionSelected = {
                            prefs.setDefaultTab(it)
                        }
                    )
                )
            )
            SettingSection(
                title = "Data",
                tiles = listOf(
                    SettingTile.ActionTile(
                        leading = { SettingsTileIcon(R.drawable.upload_24px) },
                        title = "Export app data",
                        onClick = {
                            isExportDialogOpen = true
                        }
                    ),
                    SettingTile.ActionTile(
                        leading = { SettingsTileIcon(R.drawable.download_24px) },
                        title = "Import app data",
                        onClick = {
                            isWarningImportDialogOpen = true
                        }
                    )
                )
            )
        }
    }


    DialogBasic(
        show = isExportDialogOpen,
        onConfirm = {
            val intent = createNewDocumentIntent()
            exportLauncher(intent)
        },
        onDismiss = {
            isExportDialogOpen = false
        },
        title = "Export data",
        confirmBtnDisabled = !exportWatchlistChecked && !exportMovieListChecked,
    ) {
        Column() {
            CheckboxRow(
                label = "Export watchlist",
                checked = exportWatchlistChecked
            ) { exportWatchlistChecked = it }
            CheckboxRow(
                label = "Export movie lists",
                checked = exportMovieListChecked
            ) { exportMovieListChecked = it }

        }
    }

    TextAlertDialog(
        show = isWarningImportDialogOpen,
        onDismiss = {
            isWarningImportDialogOpen = false
        },
        title = "Import data",
        onConfirm = {
            val intent = createOpenDocumentIntent()
            importLauncher(intent)
        },
        message = "Importing data will replace the current data. Are you sure you want to continue?"
    )
}

