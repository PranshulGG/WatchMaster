package com.pranshulgg.watchmaster.prefs

data class AppPrefsState(
    val useExpressive: Boolean,
    val setUseExpressive: (Boolean) -> Unit,

    val darkTheme: Boolean,
    val setDarkTheme: (Boolean) -> Unit,
)
