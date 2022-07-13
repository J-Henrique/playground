package com.jhbb.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhbb.kotlinflow.ui.theme.KotlinFlowTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View system way to collect flows
//        collectLatestLifecycleFlow(viewModel.stateFlow) {
//            binding.tvCounter.text = it.toString()
//        }

        setContent {
            KotlinFlowTheme {
                val viewModel = viewModel<MainViewModel>()
                val count = viewModel.stateFlow.collectAsState(initial = 0)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Button(onClick = { viewModel.incrementCounter() }) {
                            Text(text = "Counter: ${count.value}")
                        }
                    }
                }
            }
        }
    }
}

fun <T> ComponentActivity.collectLatestLifecycleFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit,
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}