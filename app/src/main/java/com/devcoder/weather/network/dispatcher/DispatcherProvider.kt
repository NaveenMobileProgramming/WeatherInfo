package com.devcoder.weather.network.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val network: CoroutineDispatcher
    val database: CoroutineDispatcher
    val computation: CoroutineDispatcher
}