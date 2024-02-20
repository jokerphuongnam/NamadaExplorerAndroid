package com.monsjoker.namadaexplorer.data.domain

sealed interface DataState<T> {
    class Loading<T> : DataState<T>
    class Success<T>(val data: T) : DataState<T>
    class Error<T>(val error: Throwable) : DataState<T>

    val isLoading: Boolean
        get() {
            return this is Loading
        }

    val value: T?
        get() {
            val successState = this as? Success ?: return null
            return successState.data
        }

    val exception: Throwable?
        get() {
            val errorState = this as? Error ?: return null
            return errorState.error
        }

    companion object {
        fun <T> loadMore(dataState: DataState<List<T>>, moreData: List<T>): DataState<List<T>> {
            return when (dataState) {
                is Success -> Success(dataState.data + moreData)
                else -> dataState
            }
        }
    }
}