package com.jhbb.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val countDownFlow = flow {
        val startingValue = 5
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }.flowOn(dispatchers.main)

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
//        collectFlow()
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect {
                delay(2000)
                println("FIRST FLOW: $it")
            }
        }
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect {
                delay(3000)
                println("SECOND FLOW: $it")
            }
        }
        squareNumber(3)
    }

    fun incrementCounter() {
        _stateFlow.value += 1
    }

    fun squareNumber(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _sharedFlow.emit(number * number)
        }
    }

    private fun collectFlow() {
        val flow1 = flow {
            emit("Appetizer")
            delay(500)
            emit("Main dish")
            delay(1000)
            emit("Dessert")
        }
        viewModelScope.launch {
            flow1
                .onEach {
                    println("Delivered: $it")
                }
                .collect {
                    println("Eating: $it")
                    delay(2000)
                }
        }
    }
}