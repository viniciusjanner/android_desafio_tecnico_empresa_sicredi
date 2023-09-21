package com.viniciusjanner.desafio.sicredi.framework.di

import com.viniciusjanner.desafio.sicredi.BuildConfig
import com.viniciusjanner.desafio.sicredi.framework.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}
