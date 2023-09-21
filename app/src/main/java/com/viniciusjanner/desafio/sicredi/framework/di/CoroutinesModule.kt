package com.viniciusjanner.desafio.sicredi.framework.di

import com.viniciusjanner.desafio.core.usecase.base.AppCoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(appCoroutinesDispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}
