package com.minerkit.android.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Emerald = Color(0xFF18D49B)
val Amber = Color(0xFFFFC857)
val Danger = Color(0xFFFF5D6C)
val Ink = Color(0xFF07131A)
val Surface = Color(0xFF10232C)

@Composable fun MinerKitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Emerald, secondary = Amber, error = Danger,
            background = Ink, surface = Surface, onBackground = Color(0xFFEAF7F3), onSurface = Color(0xFFEAF7F3)
        ),
        content = content
    )
}
