package com.example.buyandsell.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = SpotifyGreen,
    background = SpotifyBlack,
    surface = SpotifyBlack,
    onPrimary = Color.Black,
    onSecondary = SpotifyWhite,
    onBackground = SpotifyWhite,
    onSurface = SpotifyWhite,
)

@Composable
fun BuyAndSellTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Parameter can be used for light/dark mode switching later
    content: @Composable () -> Unit
) {
    // Forcing dark theme for now to match the design request
    val colors = DarkColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
