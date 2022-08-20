package com.devcoder.weather.di.modules


import com.devcoder.weather.network.AppInterceptor
import com.devcoder.weather.network.RemoteDataSource
import com.devcoder.weather.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideOkHttpClient(interceptor: AppInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    @Provides
    fun provideApiService(remoteDataSource: RemoteDataSource): ApiService {
        return remoteDataSource.buildApi()
    }

    @Provides
    fun provideRemoteDataSource(client: OkHttpClient): RemoteDataSource {
        return RemoteDataSource(okHttpClient = client)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}