package com.mellophi.calculator.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// --- THEME DEFINITIONS ---
// In a real project, this would be in ui/theme/Color.kt
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkOnBackground = Color(0xFFE6E1E5)
private val DarkOnSurface = Color(0xFFE6E1E5)
private val DarkPrimary = Color(0xFFD0BCFF)
private val DarkSurfaceVariant = Color(0xFF383838)
private val DarkOnSurfaceVariant = Color.White

private val LightBackground = Color(0xFFFFF9C4) // Light Yellow
private val LightSurface = Color(0xFFFFFFFF)
private val LightOnBackground = Color(0xFF1C1B1F)
private val LightOnSurface = Color(0xFF1C1B1F)
private val LightPrimary = Color(0xFF6750A4)
private val LightSurfaceVariant = Color(0xFFE7E0EC)
private val LightOnSurfaceVariant = Color.Black

val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    primary = DarkPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant
)

val LightColorScheme = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    primary = LightPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant
)
