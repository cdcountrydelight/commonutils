package com.cd.utility.model

sealed class Result<T> {
    class Loading<T> : Result<T>()
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val code: Int? = null, val detail: String? = null) : Result<T>()
    class NetworkError<T> : Result<T>()

    companion object {
        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) = Success(data)

        fun <T> error(code: Int, message: String) = Error<T>(code, message)
    }
}