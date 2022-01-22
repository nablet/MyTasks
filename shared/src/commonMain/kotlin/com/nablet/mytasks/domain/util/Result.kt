package com.nablet.mytasks.domain.util

sealed class Resource<out Success, out Failure>
data class Success<out Success>(val value: Success) : Resource<Success, Nothing>()
data class Failure<out Failure>(val exception: Failure) : Resource<Nothing, Failure>()

typealias Result<T> = Resource<T, Throwable>
