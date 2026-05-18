package com.axiel7.moelist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamicColorScheme
import com.materialkolor.dynamiccolor.ColorSpec

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MoeListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColorSeed: Color? = null,
    useBlackColors: Boolean = false,
    paletteStyle: PaletteStyle = PaletteStyle.Expressive,
    content: @Composable () -> Unit,
) {
    val colorScheme = remember(dynamicColorSeed, darkTheme, useBlackColors, paletteStyle) {
        when {
            dynamicColorSeed != null -> {
                dynamicColorScheme(
                    primary = dynamicColorSeed,
                    isDark = darkTheme,
                    isAmoled = useBlackColors,
                    style = paletteStyle,
                    specVersion = ColorSpec.SpecVersion.SPEC_2025,
                )
            }

            else -> dynamicColorScheme(
                seedColor = Brand.seed,
                isDark = darkTheme,
                isAmoled = useBlackColors,
                style = paletteStyle,
                specVersion = ColorSpec.SpecVersion.SPEC_2025
            )
        }
    }

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        content = content
    )
}
