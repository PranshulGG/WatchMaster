package com.pranshulgg.watchmaster.prefs

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppPrefs = staticCompositionLocalOf<AppPrefsState> {
    error("LocalAppPrefs not provided")
}
