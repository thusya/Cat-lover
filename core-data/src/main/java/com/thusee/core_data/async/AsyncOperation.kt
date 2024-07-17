package com.thusee.core_data.async

sealed class AsyncOperation<out T> {
    data object Loading: AsyncOperation<Nothing>()

    data class Success<T>(
        val asyncOp: T,
    ): AsyncOperation<T>()

    data class Failure<T>(
        val asyncOp: T,
        val throwable: Throwable
    ): AsyncOperation<T>()
}