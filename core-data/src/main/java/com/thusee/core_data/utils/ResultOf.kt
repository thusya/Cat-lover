package com.thusee.core_data.utils

sealed class ResultOf<out T> {
    data class Success<T>(val value: T): ResultOf<T>()

    data class Failure(
        val throwable: Throwable
    ): ResultOf<Nothing>()
}