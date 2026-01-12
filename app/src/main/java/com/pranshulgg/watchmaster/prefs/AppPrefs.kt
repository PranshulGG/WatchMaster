package com.pranshulgg.watchmaster.prefs

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.pranshulgg.watchmaster.helpers.PreferencesHelper

object AppPrefs {

    private val _useExpressive = mutableStateOf(true)
    private val _darkTheme = mutableStateOf(false)

    fun init(context: Context) {
        PreferencesHelper.init(context)

        _useExpressive.value =
            PreferencesHelper.getBool("useExpressiveColor") ?: true
        _darkTheme.value =
            PreferencesHelper.getBool("darkTheme") ?: false
    }

    @Composable
    fun state(): AppPrefsState = AppPrefsState(
        useExpressive = _useExpressive.value,
        setUseExpressive = {
            _useExpressive.value = it
            PreferencesHelper.setBool("useExpressiveColor", it)
        },

        darkTheme = _darkTheme.value,
        setDarkTheme = {
            _darkTheme.value = it
            PreferencesHelper.setBool("darkTheme", it)
        }
    )
}
