package com.circuit.cmp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = PretendardTypography(),
        colorScheme = LightColorScheme, // Change if you need dark mode support
        content = content
    )
}
