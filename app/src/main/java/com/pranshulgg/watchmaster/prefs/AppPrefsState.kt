package com.pranshulgg.watchmaster.prefs

import com.pranshulgg.watchmaster.model.ThemeVariantType

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
    val setThemeVariant: (ThemeVariantType) -> Unit
)
