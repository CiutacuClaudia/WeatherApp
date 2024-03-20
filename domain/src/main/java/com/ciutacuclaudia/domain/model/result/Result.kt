package com.ciutacuclaudia.domain.model.result

import com.ciutacuclaudia.data.remote.model.result.ResultEntity

enum class ErrorCause {
    EMPTY_LIST,
    UNKNOWN_ERROR
}

sealed class Result {

    class Success<T>(val payload: T? = null) : Result() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ResultEntity.Success<*>) return false

            if (payload != other.payload) return false

            return true
        }

        override fun hashCode(): Int {
            return payload?.hashCode() ?: 0
        }
    }

    class Error(val errorCause: ErrorCause) : Result() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Error) return false

            if (errorCause != other.errorCause) return false

            return true
        }

        override fun hashCode(): Int {
            return errorCause.hashCode()
        }
    }
}