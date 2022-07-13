package com.jhbb.kotlinflow

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val testDispatcherProvider = TestDispatcherProvider()

    @Before
    fun setUp() {
        viewModel = MainViewModel(testDispatcherProvider)
    }

    @Test
    fun `countDownFlow, properly counts down from 5 to 0`() = runBlocking {
        viewModel.countDownFlow.test {
            for (i in 5 downTo 0) {
                testDispatcherProvider.testDispatcher.advanceTimeBy(1000)
                val emission = awaitItem()
                assertThat(emission).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `squareNumber, number properly squared`() = runBlocking {
        val job = launch {
            viewModel.sharedFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(9)
            }
        }
        viewModel.squareNumber(3)
        job.join()
        job.cancel()
    }
}