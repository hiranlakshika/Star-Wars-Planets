package com.sysco.shared.core.domain.model.error


sealed interface DataError : Error {
    enum class LocalDataError : DataError {
        NOT_FOUND
    }

    enum class NetworkError : DataError {
        NO_INTERNET,
        SERVER_ERROR,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        REQUEST_TIMEOUT,
        CONNECTION_ERROR,
        UNKNOWN
    }
}