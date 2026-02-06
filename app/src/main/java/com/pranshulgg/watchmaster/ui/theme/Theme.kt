package com.pranshulgg.watchmaster.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.pranshulgg.watchmaster.model.StatusColor
import com.pranshulgg.watchmaster.model.StatusColors
import com.pranshulgg.watchmaster.model.ThemeVariantType


val LocalStatusColors = staticCompositionLocalOf {
    StatusColors(
        success = StatusColor(Color(0xFF16520E), Color(0xFFB1F49D)),
        pending = StatusColor(Color(0xFFD4D4D4), Color(0xFF000000)),
        warning = StatusColor(Color(0xFF5D4200), Color(0xFFFFDEA4)),
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WatchMasterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    seedColor: Color = Color.Green,
    themeVariantType: ThemeVariantType,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> {
            rememberDynamicColorScheme(
                seedColor = seedColor,
                isDark = darkTheme,
                specVersion = ColorSpec.SpecVersion.SPEC_2025,
                style = themeVariantType.paletteStyle,
            )
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    val statusColors = if (darkTheme) {
        StatusColors(
            success = StatusColor(Color(0xFF16520E), Color(0xFFB1F49D)),
            pending = StatusColor(Color(0xFFD4D4D4), Color(0xFF000000)),
            warning = StatusColor(Color(0xFF5D4200), Color(0xFFFFDEA4)),
        )
    } else {
        StatusColors(
            success = StatusColor(Color(0xFFC4F18C), Color(0xFF304F00)),
            pending = StatusColor(Color(0xFF3B3B3B), Color(0xFFFFFFFF)),
            warning = StatusColor(Color(0xFFFFDEA4), Color(0xFF5D4200)),
        )
    }


    CompositionLocalProvider(
        LocalStatusColors provides statusColors
    ) {
        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            motionScheme = MotionScheme.expressive(),
            content = content
        )
    }

}