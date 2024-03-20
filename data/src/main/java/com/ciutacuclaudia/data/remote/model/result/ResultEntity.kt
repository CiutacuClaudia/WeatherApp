package com.ciutacuclaudia.data.remote.model.result

enum class ErrorCauseEntity {
    EMPTY_LIST,
    UNKNOWN_ERROR
}

sealed class ResultEntity {

    class Success<T>(val payload: T? = null) : ResultEntity() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Success<*>) return false

            if (payload != other.payload) return false

            return true
        }

        override fun hashCode(): Int {
            return payload?.hashCode() ?: 0
        }
    }

    class Error(val errorCauseEntity: ErrorCauseEntity) : ResultEntity() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Error) return false

            if (errorCauseEntity != other.errorCauseEntity) return false

            return true
        }

        override fun hashCode(): Int {
            return errorCauseEntity.hashCode()
        }
    }
}
