package com.devcoder.weather.network.errors

import com.devcoder.weather.network.entities.ErrorEntity
import com.devcoder.weather.network.entities.NetworkErrorEntity
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

private const val HTTP_UNPROCESSABLE_ENTITY = 422

open class AppErrorHandler @Inject constructor() : ErrorHandler {

    override fun toErrorEntity(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is HttpException -> fromHttpExceptionCode(throwable)
            is UnknownHostException -> NetworkErrorEntity.WrongServerUrl
            is OutOfMemoryError -> NetworkErrorEntity.OutOfMemoryError
            else -> NetworkErrorEntity.NotImplementedError(throwable)
        }
    }

    private fun fromHttpExceptionCode(httpException: HttpException): ErrorEntity {
        return when (httpException.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> NetworkErrorEntity.UnauthorizedError
            HttpURLConnection.HTTP_UNAVAILABLE -> NetworkErrorEntity.ServiceUnavailable
            HttpURLConnection.HTTP_INTERNAL_ERROR -> NetworkErrorEntity.InternalServerError
            HttpURLConnection.HTTP_NOT_FOUND -> NetworkErrorEntity.NotFoundError
            HttpURLConnection.HTTP_NOT_IMPLEMENTED -> NetworkErrorEntity.NotImplementedError()
            HttpURLConnection.HTTP_CLIENT_TIMEOUT -> NetworkErrorEntity.ClientTimeoutError
            HTTP_UNPROCESSABLE_ENTITY -> handleUnprocessableEntity(httpException)
            else -> NetworkErrorEntity.UnknownError
        }
    }

    protected open fun handleUnprocessableEntity(httpException: HttpException): ErrorEntity {
        return NetworkErrorEntity.UnknownError
    }
}