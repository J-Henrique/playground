package com.jhbb.coroutinesexceptions

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jhbb.coroutinesexceptions.api.Failure
import com.jhbb.coroutinesexceptions.api.Loading
import com.jhbb.coroutinesexceptions.api.Success
import com.jhbb.coroutinesexceptions.utils.LoadingDialog

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private var loadingDialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindButtons()
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.message.observe(this, Observer {
            when (it) {
                is Loading -> {
                    loadingDialog.show(supportFragmentManager, null)
                }

                is Success -> {
                    loadingDialog.dismiss()
                    AlertDialog.Builder(this@MainActivity)
                            .setMessage("Success: " + it.data)
                            .show()
                }

                is Failure -> {
                    loadingDialog.dismiss()
                    AlertDialog.Builder(this@MainActivity)
                            .setMessage("Error: " + it.exception)
                            .show()
                }
            }
        })
    }

    private fun bindButtons() {
        findViewById<Button>(R.id.btDialog1).setOnClickListener {
            viewModel.fetchSuccessMessage()
        }
        findViewById<Button>(R.id.btDialog2).setOnClickListener {
            viewModel.fetchErrorMessage()
        }
        findViewById<Button>(R.id.btDialog3).setOnClickListener {
            viewModel.fetchFromSequentialRequests()
        }
        findViewById<Button>(R.id.btDialog4).setOnClickListener {
            viewModel.fetchFromParallelRequests1()
        }
        findViewById<Button>(R.id.btDialog5).setOnClickListener {
            viewModel.fetchFromParallelRequests2()
        }
    }
}