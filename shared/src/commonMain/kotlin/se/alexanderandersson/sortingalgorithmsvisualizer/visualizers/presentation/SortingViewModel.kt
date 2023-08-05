package se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.presentation

import androidx.compose.runtime.mutableStateListOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.domain.usecase.BubbleSortUseCase
import se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.presentation.state.ListUiItem

enum class SortingState {
    IDLE, RUNNING, PAUSED, COMPLETED
}

class SortingViewModel(
    private val bubbleSortUseCase: BubbleSortUseCase = BubbleSortUseCase()
): ViewModel() {

    private val _sortingState: MutableStateFlow<SortingState> = MutableStateFlow(SortingState.IDLE)
    val sortingState: StateFlow<SortingState> = _sortingState

    var listToSort = mutableStateListOf<ListUiItem>()
    var currentIterationIndex = 0
    private var resumeItemIndex = 0

    init {
        for(i in 0 until 9) {
            val value = (1 .. 100).random()
            listToSort.add(
                ListUiItem(
                    id = i,
                    isCurrentlyCompared = false,
                    value = value
                )
            )
        }
    }

    fun regenerateList() {
        listToSort.clear()
        for(i in 0 until 9) {
            val value = (1 .. 100).random()
            listToSort.add(
                ListUiItem(
                    id = i,
                    isCurrentlyCompared = false,
                    value = value
                )
            )
        }
        _sortingState.value = SortingState.IDLE
    }

    fun startSorting() {
        if (_sortingState.value == SortingState.RUNNING) return
        _sortingState.value = SortingState.RUNNING

        viewModelScope.launch {
            bubbleSortUseCase(listToSort.map {
                it.value
            }.toMutableList(), currentIterationIndex, resumeItemIndex).collect { sortInfo ->
                if(sortInfo.isDone) {
                    _sortingState.value = SortingState.COMPLETED
                    return@collect
                }
                if (_sortingState.value == SortingState.PAUSED) {
                    suspendUntilResumed()
                }

                val currentItemIndex = sortInfo.currentItem

                listToSort[currentItemIndex] = listToSort[currentItemIndex]
                    .copy(isCurrentlyCompared = true)

                listToSort[currentItemIndex+1] = listToSort[currentItemIndex+1]
                    .copy(isCurrentlyCompared = true)

                if(sortInfo.shouldSwap) {
                    val firstItem = listToSort[currentItemIndex]
                        .copy(isCurrentlyCompared = false)

                    listToSort[currentItemIndex] = listToSort[currentItemIndex+1]
                        .copy(isCurrentlyCompared = false)

                    listToSort[currentItemIndex+1] = firstItem

                }

                if(sortInfo.hadNoEffect) {
                    listToSort[currentItemIndex] = listToSort[currentItemIndex]
                        .copy(isCurrentlyCompared = false)

                    listToSort[currentItemIndex+1] = listToSort[currentItemIndex+1]
                        .copy(isCurrentlyCompared = false)
                }

                currentIterationIndex = sortInfo.currentIteration
                resumeItemIndex = sortInfo.currentItem
            }
        }
    }

    fun pauseSorting() {
        _sortingState.value = SortingState.PAUSED
    }

    private suspend fun suspendUntilResumed() {
        sortingState.collect {
            if (it == SortingState.RUNNING) {
                return@collect
            }
        }
    }
}