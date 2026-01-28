package com.pranshulgg.watchmaster.prefs

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.pranshulgg.watchmaster.helpers.PreferencesHelper
import com.pranshulgg.watchmaster.model.ThemeVariantType

object AppPrefs {
    private val _appTheme = mutableStateOf("System")
    private val _themeColor = mutableStateOf("#2196f3")
    private val _isCustomTheme = mutableStateOf(false)
    private val _useDynamicColor = mutableStateOf(false)

    private val _themeVariant =
        mutableStateOf(ThemeVariantType.EXPRESSIVE)


    fun initPrefs(context: Context) {
        PreferencesHelper.init(context)

        _appTheme.value =
            PreferencesHelper.getString("app_theme") ?: "System"
        _themeColor.value = PreferencesHelper.getString("theme_color") ?: "#2196f3"
        _isCustomTheme.value = PreferencesHelper.getBool("isCustomTheme") ?: false
        _useDynamicColor.value = PreferencesHelper.getBool("useDynamicColor") ?: false
        _themeVariant.value =
            PreferencesHelper.getString("theme_variant")
                ?.let {
                    runCatching { ThemeVariantType.valueOf(it) }.getOrNull()
                }
                ?: ThemeVariantType.EXPRESSIVE
    }

    @Composable
    fun state(): AppPrefsState = AppPrefsState(

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
        },

        useDynamicColor = _useDynamicColor.value,
        setDynamicColor = {
            _useDynamicColor.value = it
            PreferencesHelper.setBool("useDynamicColor", it)
        },

        themeVariant = _themeVariant.value,
        setThemeVariant = {
            _themeVariant.value = it
            PreferencesHelper.setString("theme_variant", it.name)
        },

        )
}
