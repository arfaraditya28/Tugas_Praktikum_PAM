package com.example.myprofileapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light: skema bawaan.
private val LightColors = lightColorScheme()

// Dark: dibuat lebih lembut dari bawaan (#141218 yang nyaris hitam pekat).
// Background slate gelap + card sedikit lebih terang agar konten tetap terlihat.
private val DarkColors = darkColorScheme(
    background = Color(0xFF1C2333),
    surface = Color(0xFF1C2333),
    surfaceVariant = Color(0xFF2A3348),
    surfaceContainerLow = Color(0xFF232B3D),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFFD4DBE5)
)

/**
 * Theme Profile App. [darkTheme] berasal dari ProfileViewModel,
 * bukan variabel lokal Composable.
 */
@Composable
fun ProfileTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
