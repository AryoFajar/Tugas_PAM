package com.example.tugas4.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkProfileColors = darkColorScheme(
    primary = Color(0xFFC62828),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF7A2E17),
    background = Color(0xFF0B0908),
    onBackground = Color(0xFFF5EDEA),
    surface = Color(0xFF1A1413),
    onSurface = Color(0xFFF5EDEA),
    onSurfaceVariant = Color(0xFFBCAAA4)
)

private val LightProfileColors = lightColorScheme(
    primary = Color(0xFFC62828),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE64A19),
    background = Color(0xFFFFF8F6),
    onBackground = Color(0xFF231917),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF231917),
    onSurfaceVariant = Color(0xFF6D4C41)
)

@Composable
fun ProfileTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val target = if (darkTheme) DarkProfileColors else LightProfileColors
    val spec = tween<Color>(durationMillis = 400)

    val colorScheme = target.copy(
        primary = animateColorAsState(target.primary, spec, label = "primary").value,
        primaryContainer = animateColorAsState(target.primaryContainer, spec, label = "primaryContainer").value,
        background = animateColorAsState(target.background, spec, label = "background").value,
        onBackground = animateColorAsState(target.onBackground, spec, label = "onBackground").value,
        surface = animateColorAsState(target.surface, spec, label = "surface").value,
        onSurface = animateColorAsState(target.onSurface, spec, label = "onSurface").value,
        onSurfaceVariant = animateColorAsState(target.onSurfaceVariant, spec, label = "onSurfaceVariant").value
    )

    MaterialTheme(colorScheme = colorScheme, content = content)
}