package com.devcoder.weather.repo


import com.devcoder.weather.R
import com.devcoder.weather.network.ResponseResult
import com.devcoder.weather.network.dispatcher.DispatcherProvider
import com.devcoder.weather.network.entities.ErrorEntity
import com.devcoder.weather.network.entities.NetworkErrorEntity
import com.devcoder.weather.network.errors.ErrorHandler
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

open class BaseRepository(
    private val errorHandler: ErrorHandler,
    private val dispatcherProvider: DispatcherProvider,
) {

    suspend fun <Input : Any> execute(
        block: suspend () -> Response<Input>,
    ): ResponseResult<Input> {
        return withContext(dispatcherProvider.network) {
            try {
                val x = block()
                calculateResult(x)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
                ResponseResult.Error(errorHandler.toErrorEntity(e))
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseResult.Error(errorHandler.toErrorEntity(e))
            }
        }
    }

    private fun <Input : Any> calculateResult(
        data: Response<Input>,
    ): ResponseResult<Input> {
        return data.code().takeIf {
            it !in CODE_SUCCESS_START..CODE_SUCCESS_END
        }?.let {
            if (it in CODE_REDIRECT_START..CODE_REDIRECT_END) {
                val location = data.raw().header("Location")
                if (location != null) {
                    val newLocation = location.split("/player_api.php".toRegex()).toTypedArray()
                    ResponseResult.Redirection(newLocation[0])
                } else {
                    ResponseResult.Error(errorHandler.toErrorEntity(HttpException(data)))
                }
            } else {
                ResponseResult.Error(errorHandler.toErrorEntity(HttpException(data)))
            }
        } ?: run {
            data.body()?.let {
                ResponseResult.Success(it)
            } ?: run {
                ResponseResult.Error(NetworkErrorEntity.EmptyResponseError)
            }
        }
    }

    companion object {
        private const val CODE_SUCCESS_START = 200
        private const val CODE_SUCCESS_END = 299
        private const val CODE_REDIRECT_END = 302
        private const val CODE_REDIRECT_START = 301
    }

    fun handleError(error: ErrorEntity): Int {
        return when (error) {
            is NetworkErrorEntity.NoConnectionError -> R.string.error_server_no_internet_connection
            else -> R.string.error_server_internal
        }
    }
}
