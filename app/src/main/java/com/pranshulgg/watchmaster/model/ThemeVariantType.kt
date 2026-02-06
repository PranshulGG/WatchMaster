package com.pranshulgg.watchmaster.model

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle

enum class ThemeVariantType(
    val paletteStyle: PaletteStyle
) {
    TONAL_SPOT(PaletteStyle.TonalSpot),
    NEUTRAL(PaletteStyle.Neutral),
    VIBRANT(PaletteStyle.Vibrant),
    EXPRESSIVE(PaletteStyle.Expressive)
}

data class StatusColor(
    val bg: Color,
    val on: Color
)

data class StatusColors(
    val success: StatusColor,
    val pending: StatusColor,
    val warning: StatusColor,
)

