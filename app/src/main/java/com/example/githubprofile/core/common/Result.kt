package com.example.githubprofile.core.common

sealed class Result<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(throwable: Throwable) : Result<T>(throwable = throwable)
    class Loading<T> : Result<T>()
}