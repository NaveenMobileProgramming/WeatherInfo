package com.devcoder.weather.network

import com.devcoder.weather.models.CurrentWeatherResponse
import com.devcoder.weather.utils.AppConstant
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("weather")
    suspend fun weatherInfo(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") appid: String = AppConstant.API_KEY,
    ): Response<CurrentWeatherResponse>
}