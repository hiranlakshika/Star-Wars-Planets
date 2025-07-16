package com.sysco.starwarsplanets.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlue,
    onPrimary = JetBlack,
    secondary = MintGreen,
    onSecondary = JetBlack,
    tertiary = PeachOrange,
    onTertiary = JetBlack,
    background = RangoonGreen,
    onBackground = PureWhite,
    surface = RangoonGreen,
    surfaceVariant = Myrtle,
    onSurfaceVariant = FogGreen,
    onSurface = PureWhite,
    error = DarkErrorRed,
    onError = JetBlack
)

private val LightColorScheme = lightColorScheme(
    primary = OceanBlue,
    onPrimary = PureWhite,
    secondary = ForestGreen,
    onSecondary = PureWhite,
    tertiary = SunnyOrange,
    onTertiary = PureWhite,
    background = GhostWhite,
    onBackground = CharcoalGray,
    surface = GhostWhite,
    onSurface = CharcoalGray,
    surfaceVariant = Peppermint,
    onSurfaceVariant = GladeGreen,
    error = LightErrorRed,
    onError = PureWhite
)

@Composable
fun StarWarsPlanetsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}