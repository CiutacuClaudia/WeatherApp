package com.ciutacuclaudia.data.remote.model.result

enum class ErrorCauseEntity {
    EMPTY_LIST,
    UNKNOWN_ERROR
}

sealed class ResultEntity {

    class Success<T>(val payload: T? = null) : ResultEntity()

    class Error(val errorCauseEntity: ErrorCauseEntity) : ResultEntity()
}