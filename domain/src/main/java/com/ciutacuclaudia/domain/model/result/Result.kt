package com.ciutacuclaudia.domain.model.result

enum class ErrorCause {
    EMPTY_LIST,
    UNKNOWN_ERROR
}

sealed class Result {

    class Success<T>(val payload: T? = null) : Result()

    class Error(val errorCause: ErrorCause) : Result()
}