package com.viniciusjanner.desafio.sicredi.framework.di

import com.viniciusjanner.desafio.core.data.repository.EventDataSourceRemote
import com.viniciusjanner.desafio.core.data.repository.EventRepository
import com.viniciusjanner.desafio.sicredi.framework.remote.EventDataSourceRetrofit
import com.viniciusjanner.desafio.sicredi.framework.repository.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface EventsRepositoryModule {

    @Binds
    fun bindRemoteDataSource(dataSource: EventDataSourceRetrofit): EventDataSourceRemote

    @Binds
    fun bindEventsRepository(repository: EventRepositoryImpl): EventRepository
}
