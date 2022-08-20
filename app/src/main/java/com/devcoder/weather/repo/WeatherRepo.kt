package com.devcoder.weather.repo

import com.devcoder.weather.models.CurrentWeatherResponse
import com.devcoder.weather.network.RemoteDataSource
import com.devcoder.weather.network.ResponseResult
import com.devcoder.weather.network.dispatcher.DispatcherProvider
import com.devcoder.weather.network.errors.ErrorHandler
import com.devcoder.weather.network.map
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    errorHandler: ErrorHandler,
    private val dispatcherProvider: DispatcherProvider,
    private val remoteDataSource: RemoteDataSource,
) : BaseRepository(errorHandler, dispatcherProvider) {

    suspend fun getWeatherInfo(lat: Double, lon: Double): ResponseResult<CurrentWeatherResponse> {
        return execute {
            remoteDataSource.buildApi().weatherInfo(lat, lon)
        }.map {
            it
        }
    }
}