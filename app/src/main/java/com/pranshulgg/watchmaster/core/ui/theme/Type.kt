package com.pranshulgg.watchmaster.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R


@OptIn(ExperimentalTextApi::class)
val GoogleFlexRegular = FontFamily(
    Font(
        resId = R.font.google_sans_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.Setting("wght", 400f),
            FontVariation.Setting("ROND", 50f)
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val GoogleFlexMedium = FontFamily(
    Font(
        resId = R.font.google_sans_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.Setting("wght", 500f),
            FontVariation.Setting("ROND", 50f)
        )
    )
)


@OptIn(ExperimentalTextApi::class)
val GoogleFlexBoldRounded = FontFamily(
    Font(
        R.font.google_sans_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.Setting("ROND", 100f),
            FontVariation.Setting("wght", 1000f),
        )
    )
)


val AppTypography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 57.sp,
            lineHeight = 64.sp
        ),
        displayMedium = displayMedium.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),
        displaySmall = displaySmall.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),
        headlineLarge = headlineLarge.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 32.sp,
            lineHeight = 40.sp
        ),
        headlineMedium = headlineMedium.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        headlineSmall = headlineSmall.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        titleLarge = titleLarge.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        titleMedium = titleMedium.copy(
            fontFamily = GoogleFlexMedium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        titleSmall = titleSmall.copy(
            fontFamily = GoogleFlexMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodyLarge = bodyLarge.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium = bodyMedium.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodySmall = bodySmall.copy(
            fontFamily = GoogleFlexRegular,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        labelLarge = labelLarge.copy(
            fontFamily = GoogleFlexMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelMedium = labelMedium.copy(
            fontFamily = GoogleFlexMedium,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        labelSmall = labelSmall.copy(
            fontFamily = GoogleFlexMedium,
            fontSize = 11.sp,
            lineHeight = 16.sp
        ),
    )
}