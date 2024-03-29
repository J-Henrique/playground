package com.jhbb.kotlinflow.combinezipmerge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhbb.kotlinflow.ui.theme.KotlinFlowTheme

class CombineZipMergeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotlinFlowTheme {
                val viewModel = viewModel<CombineZipMergeViewModel>()
                Column {
                    Text(text = viewModel.numberString)
                    Button(onClick = { viewModel.pushItems() }) {
                        Text(text = "Push items")
                    }
                }
            }
        }
    }
}