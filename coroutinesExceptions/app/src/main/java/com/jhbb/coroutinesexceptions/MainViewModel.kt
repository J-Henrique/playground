package com.jhbb.coroutinesexceptions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhbb.coroutinesexceptions.api.*
import kotlinx.coroutines.*
import kotlin.Exception

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    private val _message = MutableLiveData<Response<String>>()
    val message: LiveData<Response<String>> = _message

    private val coroutineExceptionHandler = CoroutineExceptionHandler {
            _, throwable -> _message.value = Failure(throwable.message.toString())
    }

    // Success case of API request
    fun fetchSuccessMessage() {
        _message.value = Loading

        viewModelScope.launch {
            _message.value = Success(ApiService.getMessage())
        }
    }

    // A handler is passed to the coroutine to prevent a not caught exception
    fun fetchErrorMessage() {
        _message.value = Loading

        viewModelScope.launch(coroutineExceptionHandler) {
            _message.value = Success(ApiService.getMessageWithError())
        }
    }

    // Sequential request to the API, and a try-catch block to prevent the fail
    fun fetchFromSequentialRequests() {
        _message.value = Loading

        viewModelScope.launch {
            Log.d(TAG, "request 1")
            val request1 = ApiService.getMessage()

            Log.d(TAG, "request 2")
            val request2 = try {
                ApiService.getMessageWithError()
            } catch (ex: Exception) {
                ex.message
            }

            _message.value = Success(request1 + request2)
        }
    }

    // If one of the request fails, all children will fail
    fun fetchFromParallelRequests1() {
        _message.value = Loading

        viewModelScope.launch {
            try {
                coroutineScope {
                    Log.d(TAG, "request 1 deferred")
                    val request1Deferred = async { ApiService.getMessage() }

                    Log.d(TAG, "request 2 deferred")
                    val request2Deferred = async { ApiService.getMessageWithError() }

                    Log.d(TAG, "request 1 fetched")
                    val request1 = request1Deferred.await()

                    Log.d(TAG, "request 2 fetched")
                    val request2 = request2Deferred.await()

                    Log.d(TAG, "Result from request 1: $request1")
                    Log.d(TAG, "Result from request 2: $request2")
                    _message.value = Success(request1 + request2)
                }
            } catch (ex: Exception) {
                _message.value = Failure(ex.message.toString())
            }
        }
    }

    // If one of the request fails, the other one will remain running
    fun fetchFromParallelRequests2() {
        _message.value = Loading

        viewModelScope.launch {
            supervisorScope {
                Log.d(TAG, "request 1 deferred")
                val request1Deferred = async { ApiService.getMessage() }

                Log.d(TAG, "request 2 deferred")
                val request2Deferred = async { ApiService.getMessageWithError() }

                Log.d(TAG, "request 1 fetched")
                val request1 = request1Deferred.await()

                Log.d(TAG, "request 2 fetched")
                val request2 = try {
                    request2Deferred.await()
                } catch (ex: Exception) {
                    ex.message
                }

                Log.d(TAG, "Result from request 1: $request1")
                Log.d(TAG, "Result from request 2: $request2")
                _message.value = Success(request1 + request2)
            }
        }
    }

    /*
    - While NOT using async, we can go ahead with the try-catch or the CoroutineExceptionHandler
      and achieve anything based on our use-cases.
    - While using async, in addition to try-catch, we have
      two options: coroutineScope and supervisorScope.
    - With async, use supervisorScope with the individual try-catch for each
      task in addition to the top-level try-catch, when you want to continue with other tasks if one or some of them have failed.
    - With async, use coroutineScope with the top-level try-catch, when you
      do NOT want to continue with other tasks if any of them have failed.
    */
}