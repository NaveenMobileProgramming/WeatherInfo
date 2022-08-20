package com.devcoder.weather.network.errors

import com.devcoder.weather.network.entities.ErrorEntity


interface ErrorHandler {
    fun toErrorEntity(throwable: Throwable): ErrorEntity
}