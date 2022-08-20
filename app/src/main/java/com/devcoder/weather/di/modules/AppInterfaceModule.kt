package com.devcoder.weather.di.modules

import com.devcoder.weather.network.dispatcher.DispatcherProvider
import com.devcoder.weather.network.dispatcher.DispatcherProviderImpl
import com.devcoder.weather.network.errors.AppErrorHandler
import com.devcoder.weather.network.errors.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInterfaceModule {

    @Binds
    abstract fun bindDispatcher(impl: DispatcherProviderImpl): DispatcherProvider

    @Binds
    abstract fun bindErrorHandler(error: AppErrorHandler): ErrorHandler

//    @Binds
//    abstract fun bindLogRepository(logRepository: LogRepositoryImp): LogRepository

}