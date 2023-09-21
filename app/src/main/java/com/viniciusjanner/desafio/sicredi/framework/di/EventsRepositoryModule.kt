package com.viniciusjanner.desafio.sicredi.framework.di

import com.viniciusjanner.desafio.core.data.repository.EventsRemoteDataSource
import com.viniciusjanner.desafio.core.data.repository.EventsRepository
import com.viniciusjanner.desafio.sicredi.framework.remote.RetrofitEventsDataSource
import com.viniciusjanner.desafio.sicredi.framework.repository.EventsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface EventsRepositoryModule {

    @Binds
    fun bindRemoteDataSource(dataSource: RetrofitEventsDataSource): EventsRemoteDataSource

    @Binds
    fun bindEventsRepository(repository: EventsRepositoryImpl): EventsRepository
}
