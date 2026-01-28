package com.pranshulgg.watchmaster.model

import com.materialkolor.PaletteStyle

enum class ThemeVariantType(
    val paletteStyle: PaletteStyle
) {
    TONAL_SPOT(PaletteStyle.TonalSpot),
    NEUTRAL(PaletteStyle.Neutral),
    VIBRANT(PaletteStyle.Vibrant),
    EXPRESSIVE(PaletteStyle.Expressive)
}
