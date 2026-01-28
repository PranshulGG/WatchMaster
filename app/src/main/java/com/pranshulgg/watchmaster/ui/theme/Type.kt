package com.pranshulgg.watchmaster.ui.theme

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
val RobotoFlexRegular = FontFamily(
    Font(
        resId = R.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400),
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexMedium = FontFamily(
    Font(
        resId = R.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500),
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexBold = FontFamily(
    Font(
        resId = R.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700),
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexWide = FontFamily(
    Font(
        R.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700),
            FontVariation.width(125f)
        )
    )
)

val AppTypography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 57.sp
        ),
        displayMedium = displayMedium.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 45.sp
        ),
        displaySmall = displaySmall.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 36.sp
        ),
        headlineLarge = headlineLarge.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 32.sp
        ),
        headlineMedium = headlineMedium.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 28.sp
        ),
        headlineSmall = headlineSmall.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 24.sp
        ),
        titleLarge = titleLarge.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 22.sp
        ),
        titleMedium = titleMedium.copy(
            fontFamily = RobotoFlexMedium,
            fontSize = 16.sp
        ),
        titleSmall = titleSmall.copy(
            fontFamily = RobotoFlexMedium,
            fontSize = 14.sp
        ),
        bodyLarge = bodyLarge.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 16.sp
        ),
        bodyMedium = bodyMedium.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 14.sp
        ),
        bodySmall = bodySmall.copy(
            fontFamily = RobotoFlexRegular,
            fontSize = 12.sp
        ),
        labelLarge = labelLarge.copy(
            fontFamily = RobotoFlexMedium,
            fontSize = 14.sp
        ),
        labelMedium = labelMedium.copy(
            fontFamily = RobotoFlexMedium,
            fontSize = 12.sp
        ),
        labelSmall = labelSmall.copy(
            fontFamily = RobotoFlexMedium,
            fontSize = 16.sp
        ),
    )
}