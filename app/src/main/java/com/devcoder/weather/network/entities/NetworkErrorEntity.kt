package com.devcoder.weather.network.entities

sealed class NetworkErrorEntity : ErrorEntity {
    object ClientTimeoutError : NetworkErrorEntity()

    object InternalServerError : NetworkErrorEntity()
    object ServiceUnavailable : NetworkErrorEntity()
    object NotFoundError : NetworkErrorEntity()
    object UnauthorizedError : NetworkErrorEntity()
    object EmptyResponseError : NetworkErrorEntity()

    object NoConnectionError : NetworkErrorEntity()
    object WrongServerUrl : NetworkErrorEntity()
    object OutOfMemoryError : NetworkErrorEntity()

    class NotImplementedError(val throwable: Throwable? = null) : NetworkErrorEntity()
    object UnknownError : NetworkErrorEntity()
}
