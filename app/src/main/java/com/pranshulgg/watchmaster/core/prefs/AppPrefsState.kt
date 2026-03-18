package com.pranshulgg.watchmaster.core.prefs

import com.pranshulgg.watchmaster.core.ui.theme.ThemeVariantType

data class AppPrefsState(
    val appTheme: String,
    val setAppTheme: (String) -> Unit,

    val themeColor: String,
    val setThemeColor: (String) -> Unit,

    val isCustomTheme: Boolean,
    val useCustomTheme: (Boolean) -> Unit,

    val useDynamicColor: Boolean,
    val setDynamicColor: (Boolean) -> Unit,

    val themeVariant: ThemeVariantType,
    val setThemeVariant: (ThemeVariantType) -> Unit,

    val defaultTab: String,
    val setDefaultTab: (String) -> Unit
)
