package se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import se.alexanderandersson.sortingalgorithmsvisualizer.visualizers.domain.model.BubbleSortInfo

class BubbleSortUseCase {
    operator fun invoke(list: MutableList<Int>, currentIterationIndex: Int, itemIndex: Int): Flow<BubbleSortInfo> = flow {
        var listSizeLeftToCompare = list.size - 1

        val delay = 500L

        listSizeLeftToCompare -= currentIterationIndex

        var innerIterationIndex = currentIterationIndex
        var currentItemIndex = itemIndex

        while (listSizeLeftToCompare > 0) {
            while (currentItemIndex < listSizeLeftToCompare) {
                val currentItem = list[currentItemIndex]
                val nextItem = list[currentItemIndex + 1]

                emit(
                    BubbleSortInfo(
                        currentItem = currentItemIndex,
                        shouldSwap = false,
                        hadNoEffect = false,
                        isDone = false,
                        currentIteration = innerIterationIndex
                    )
                )
                delay(delay)
                if (currentItem > nextItem) {
                    list.swap(currentItemIndex, currentItemIndex + 1)
                    emit(
                        BubbleSortInfo(
                            currentItem = currentItemIndex,
                            shouldSwap = true,
                            hadNoEffect = false,
                            isDone = false,
                            currentIteration = innerIterationIndex
                        )
                    )
                } else {
                    emit(
                        BubbleSortInfo(
                            currentItem = currentItemIndex,
                            shouldSwap = false,
                            hadNoEffect = true,
                            isDone = false,
                            currentIteration = innerIterationIndex
                        )
                    )
                }

                delay(delay)
                currentItemIndex++
            }

            listSizeLeftToCompare -= 1
            innerIterationIndex++
            currentItemIndex = 0
        }

        emit(
            BubbleSortInfo(
                currentItem = -1,
                shouldSwap = false,
                hadNoEffect = false,
                isDone = true,
                currentIteration = innerIterationIndex
            )
        )
    }
}

fun <T> MutableList<T>.swap(indexOne: Int, indexTwo: Int) {
    val tempOne = this[indexOne]
    this[indexOne] = this[indexTwo]
    this[indexTwo] = tempOne
}