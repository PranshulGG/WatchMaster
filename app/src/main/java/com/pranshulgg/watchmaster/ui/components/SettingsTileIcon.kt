package com.pranshulgg.watchmaster.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.utils.Symbol

@Composable
fun SettingsTileIcon(icon: Int, dangerColor: Boolean = false) {
    Symbol(icon, color =  if(dangerColor) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant)
}