package com.devcoder.weather.network

import com.devcoder.weather.network.entities.ErrorEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class ResponseResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResponseResult<T>()
    data class Redirection(val url: String) : ResponseResult<Nothing>()
    data class Error(val error: ErrorEntity) : ResponseResult<Nothing>()
}

inline fun <T : Any> ResponseResult<T>.ifError(handler: (T: ErrorEntity) -> Unit): ResponseResult<T> {
    when (this) {
        is ResponseResult.Error -> handler(this.error)
        else->{}
    }
    return this
}

inline fun <T : Any> ResponseResult<T>.ifRedirection(handler: (T: String) -> Unit): ResponseResult<T> {
    when (this) {
        is ResponseResult.Redirection -> handler(this.url)
        else->{}
    }
    return this
}

inline fun <T : Any> ResponseResult<T>.returnIfError(handler: (T: ErrorEntity) -> T): ResponseResult<T> {
    return when (this) {
        is ResponseResult.Error -> ResponseResult.Success(handler(this.error))
        is ResponseResult.Success -> this
        is ResponseResult.Redirection -> this
    }
}

inline fun <T : Any> ResponseResult<T>.returnOnError(handler: (T: ErrorEntity) -> ResponseResult<T>): ResponseResult<T> {
    return when (this) {
        is ResponseResult.Error -> handler(this.error)
        is ResponseResult.Success -> this
        is ResponseResult.Redirection -> this
    }
}

inline fun <T : Any> ResponseResult<T>.ifSuccess(handler: (T) -> Unit): ResponseResult<T> {
    when (this) {
        is ResponseResult.Success -> handler(this.data)
        else->{}
    }
    return this
}

fun <T : Any> ResponseResult<T>.isSuccess(): Boolean {
    return this is ResponseResult.Success
}

fun <T : Any> ResponseResult<T>.isError(): Boolean {
    return this is ResponseResult.Error
}

fun <T : Any> ResponseResult<T>.data(): T {
    return (this as ResponseResult.Success).data
}

fun <T : Any> ResponseResult<T>.dataOrNull(): T? {
    return (this as? ResponseResult.Success)?.data
}

fun <T : Any> ResponseResult<T>.errorEntity(): ErrorEntity {
    return (this as ResponseResult.Error).error
}

inline fun <Input : Any, Output : Any> ResponseResult<Input>.map(mapper: (Input) -> Output): ResponseResult<Output> {
    return when (this) {
        is ResponseResult.Success -> ResponseResult.Success(mapper.invoke(this.data))
        is ResponseResult.Error -> ResponseResult.Error(this.error)
        is ResponseResult.Redirection -> ResponseResult.Redirection(this.url)
    }
}

suspend fun <Input : Any, Output : Any> ResponseResult<Input>.ioMap(
    dispatcher: CoroutineDispatcher,
    mapper: suspend (Input) -> Output,
): ResponseResult<Output> {
    return withContext(dispatcher) {
        return@withContext when (this@ioMap) {
            is ResponseResult.Success -> ResponseResult.Success(mapper.invoke(this@ioMap.data))
            is ResponseResult.Error -> ResponseResult.Error(this@ioMap.error)
            is ResponseResult.Redirection -> ResponseResult.Redirection(this@ioMap.url)
        }
    }
}

inline fun <Input : Any, Output : Any> ResponseResult<Input>.mapResponseResult(mapper: (Input) -> ResponseResult<Output>): ResponseResult<Output> {
    return when (this) {
        is ResponseResult.Success -> mapper.invoke(this.data)
        is ResponseResult.Error -> ResponseResult.Error(this.error)
        is ResponseResult.Redirection -> ResponseResult.Redirection(this.url)
    }
}

inline fun <Input : Any, Output : Any> ResponseResult<Input>.transform(mapper: (ResponseResult<Input>) -> ResponseResult<Output>): ResponseResult<Output> {
    return mapper.invoke(this)
}

infix fun <T : Any> T?.orError(error: ErrorEntity): ResponseResult<T> {
    this?.let {
        return ResponseResult.Success(it)
    }
    return ResponseResult.Error(error)
}
