package com.viniciusjanner.desafio.sicredi.framework.di.module

import com.viniciusjanner.desafio.sicredi.BuildConfig
import com.viniciusjanner.desafio.sicredi.framework.di.qualifier.BaseUrl
import com.viniciusjanner.desafio.sicredi.framework.network.service.EventApi
import com.viniciusjanner.desafio.sicredi.framework.network.unsafe.UnsafeOkHttpClient
import com.viniciusjanner.desafio.sicredi.framework.network.unsafe.UnsafeOkHttpClient.Companion.getConnectionSpecsList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        UnsafeOkHttpClient
            .getUnsafeOkHttpClient()
            .addInterceptor(loggingInterceptor)
            .connectionSpecs(getConnectionSpecsList())
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        @BaseUrl baseUrl: String,
    ): EventApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(EventApi::class.java)
}
