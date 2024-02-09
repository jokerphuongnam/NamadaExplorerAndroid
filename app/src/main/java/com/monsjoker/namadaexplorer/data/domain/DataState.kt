package com.monsjoker.namadaexplorer.data.domain

sealed interface DataState<T> {
    class Loading<T>: DataState<T>
    class Success<T>(val data: T): DataState<T>
    class Error<T>(val error: Exception): DataState<T>

    val value: T?
        get() {
            val successState = this as? Success ?: return null
            return successState.data
        }

    val exception: Exception?
        get() {
            val errorState = this as? Error ?: return null
            return errorState.error
        }
}