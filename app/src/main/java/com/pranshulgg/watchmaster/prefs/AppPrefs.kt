package com.pranshulgg.watchmaster.prefs

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.pranshulgg.watchmaster.helpers.PreferencesHelper

object AppPrefs {

    private val _useExpressive = mutableStateOf(true)
    private val _appTheme = mutableStateOf("System")
    private val _themeColor = mutableStateOf("#2196f3")

    private val _isCustomTheme = mutableStateOf(false)


    fun init(context: Context) {
        PreferencesHelper.init(context)

        _useExpressive.value =
            PreferencesHelper.getBool("useExpressiveColor") ?: true
        _appTheme.value =
            PreferencesHelper.getString("app_theme") ?: "System"
        _themeColor.value = PreferencesHelper.getString("theme_color") ?: "#2196f3"
        _isCustomTheme.value = PreferencesHelper.getBool("isCustomTheme") ?: false
    }

    @Composable
    fun state(): AppPrefsState = AppPrefsState(
        useExpressive = _useExpressive.value,
        setUseExpressive = {
            _useExpressive.value = it
            PreferencesHelper.setBool("useExpressiveColor", it)
        },

        appTheme = _appTheme.value,
        setAppTheme = {
            _appTheme.value = it
            PreferencesHelper.setString("app_theme", it)
        },

        themeColor = _themeColor.value,
        setThemeColor = {
            _themeColor.value = it
            PreferencesHelper.setString("theme_color", it)
        },

        isCustomTheme = _isCustomTheme.value,
        useCustomTheme = {
            _isCustomTheme.value = it
            PreferencesHelper.setBool("isCustomTheme", it)
        }

    )
}
