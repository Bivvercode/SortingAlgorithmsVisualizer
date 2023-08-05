package se.alexanderandersson.sortingalgorithmsvisualizer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import se.alexanderandersson.sortingalgorithmsvisualizer.core.presentation.AppTheme
import se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.presentation.SortingViewModel
import se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.presentation.VisualizerScreen

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            VisualizerScreen(
                sortingViewModel = SortingViewModel()
            )
        }
    }
}