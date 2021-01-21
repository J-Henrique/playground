package com.jhbb.coroutinesexceptions.api

sealed class Response<out T>
data class Success<out T>(val data: T): Response<T>()
object Loading: Response<Nothing>()
data class Failure<out T>(val exception: T): Response<T>()
