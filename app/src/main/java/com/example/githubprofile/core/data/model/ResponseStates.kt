package com.example.githubprofile.core.data.model

sealed class ResponseStates<T> {

    data class Success<T>(val response: T): ResponseStates<T>()
    data class Error<T>(val throwable: Throwable): ResponseStates<T>()
    data class Loading<T>(val isLoading: Boolean): ResponseStates<T>()
}