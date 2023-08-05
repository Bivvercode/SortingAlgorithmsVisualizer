package se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.domain.model

data class BubbleSortInfo(
    val currentItem:Int,
    val shouldSwap:Boolean,
    val hadNoEffect:Boolean,
    val isDone:Boolean,
    val currentIteration:Int
)
