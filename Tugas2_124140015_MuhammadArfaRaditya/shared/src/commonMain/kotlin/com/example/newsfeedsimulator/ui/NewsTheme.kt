package com.example.newsfeedsimulator.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Nuansa ungu: nyaman, modern, mirip template Compose Multiplatform.
private val PurplePrimary = Color(0xFF6750A4)
private val PurpleContainer = Color(0xFFEADDFF)
private val PurpleSecondary = Color(0xFF625B71)
private val AppBackgroundLight = Color(0xFFF6F1FB)

private val PurplePrimaryDark = Color(0xFFD0BCFF)
private val PurpleContainerDark = Color(0xFF4F378B)
private val AppBackgroundDark = Color(0xFF14121A)
private val AppSurfaceDark = Color(0xFF1D1B20)

private val LightColors = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = Color(0xFF21005D),
    secondary = PurpleSecondary,
    background = AppBackgroundLight,
    surface = Color.White
)

private val DarkColors = darkColorScheme(
    primary = PurplePrimaryDark,
    onPrimary = Color(0xFF381E72),
    primaryContainer = PurpleContainerDark,
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    background = AppBackgroundDark,
    surface = AppSurfaceDark
)

@Composable
fun NewsTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
