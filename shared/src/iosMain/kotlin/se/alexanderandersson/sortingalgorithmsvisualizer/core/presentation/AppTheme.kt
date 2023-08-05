package se.alexanderandersson.sortingalgorithmsvisualizer.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import se.alexanderandersson.sortingalgorithmsvisualizer.ui.theme.DarkColors
import se.alexanderandersson.sortingalgorithmsvisualizer.ui.theme.LightColors
import se.alexanderandersson.sortingalgorithmsvisualizer.ui.theme.Typography

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}