package com.pranshulgg.watchmaster.prefs

data class AppPrefsState(
    val useExpressive: Boolean,
    val setUseExpressive: (Boolean) -> Unit,

    val appTheme: String,
    val setAppTheme: (String) -> Unit,

    val themeColor: String,
    val setThemeColor: (String) -> Unit,

    val isCustomTheme: Boolean,
    val useCustomTheme: (Boolean) -> Unit,
)
