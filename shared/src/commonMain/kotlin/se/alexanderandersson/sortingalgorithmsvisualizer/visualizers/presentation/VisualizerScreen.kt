package se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VisualizerScreen(
    sortingViewModel: SortingViewModel
) {
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer

    val sortingState by sortingViewModel.sortingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(
            modifier = Modifier.size(20.dp)
        )
        Button(
            onClick = {
                when(sortingState) {
                    SortingState.IDLE -> sortingViewModel.startSorting()
                    SortingState.RUNNING -> sortingViewModel.pauseSorting()
                    SortingState.PAUSED -> sortingViewModel.startSorting()
                    SortingState.COMPLETED -> {
                        sortingViewModel.regenerateList()
                        sortingViewModel.currentIterationIndex = 0
                    }
                }
            }
        ) {
            val text = when(sortingState) {
                SortingState.IDLE -> "Start Sorting"
                SortingState.RUNNING -> "Pause Sorting"
                SortingState.PAUSED -> "Continue Sorting"
                SortingState.COMPLETED -> "Start Over"
            }
            Text(
                text,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(
                sortingViewModel.listToSort,
                key = { it.id }
            ) {
                val borderStroke = if(it.isCurrentlyCompared) {
                    BorderStroke(
                        width = 5.dp,
                        MaterialTheme.colorScheme.inversePrimary
                    )
                } else {
                    BorderStroke(
                        width = 0.dp,
                        Color.Transparent
                    )
                }

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            Color(
                                (primaryContainerColor.red * (1f-(it.value/100f))).coerceIn(0.15f,1f),
                                (primaryContainerColor.green * (1f-(it.value/100f))).coerceIn(0.15f,1f),
                                (primaryContainerColor.blue * (1f-(it.value/100f))).coerceIn(0.15f,1f)
                            ),
                            RoundedCornerShape(15.dp)
                        )
                        .border(
                            borderStroke,
                            RoundedCornerShape(15.dp)
                        )
                        .animateItemPlacement(
                            tween(300)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${it.value}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}