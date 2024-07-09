package com.casm.socialnetwork.core.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GreenAccent,
    background = DarkGray,
    onBackground = TextWhite,
    onPrimary = DarkGray,
    surface = MediumGray,
    onSurface = LightGray
)


@Composable
fun SocialNetworkTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}