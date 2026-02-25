package com.pranshulgg.watchmaster.core.ui.theme

import androidx.compose.ui.graphics.Color

data class MediaStatusColor(
    val bg: Color,
    val on: Color
)

data class MediaStatusColors(
    val success: MediaStatusColor,
    val pending: MediaStatusColor,
    val warning: MediaStatusColor,
)

