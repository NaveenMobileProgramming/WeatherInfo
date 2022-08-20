package com.devcoder.weather.network.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val network: CoroutineDispatcher get() = io
    override val database: CoroutineDispatcher get() = io
    override val computation: CoroutineDispatcher get() = io

}