package com.jhbb.coroutinesexceptions.api

import kotlinx.coroutines.delay

object ApiService {
    suspend fun getMessage(): String {
        delay(3000)
        return "Hello world!"
    }

    suspend fun getMessageWithError(): String {
        delay(3000)
        throw Exception("Network error")
    }
}