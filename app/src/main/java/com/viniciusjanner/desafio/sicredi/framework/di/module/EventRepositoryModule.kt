package com.viniciusjanner.desafio.sicredi.framework.di.module

import com.viniciusjanner.desafio.core.data.remote.EventDataSourceRemote
import com.viniciusjanner.desafio.core.data.repository.feature.checkin.EventCheckinRepository
import com.viniciusjanner.desafio.core.data.repository.feature.detail.EventDetailRepository
import com.viniciusjanner.desafio.core.data.repository.feature.list.EventListRepository
import com.viniciusjanner.desafio.sicredi.framework.remote.EventDataSourceRetrofit
import com.viniciusjanner.desafio.sicredi.framework.repository.feature.checkin.EventCheckinRepositoryImpl
import com.viniciusjanner.desafio.sicredi.framework.repository.feature.detail.EventDetailRepositoryImpl
import com.viniciusjanner.desafio.sicredi.framework.repository.feature.list.EventListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface EventRepositoryModule {

    @Binds
    fun bindEventDataSourceRemote(dataSource: EventDataSourceRetrofit): EventDataSourceRemote

    @Binds
    fun bindEventListRepository(repository: EventListRepositoryImpl): EventListRepository

    @Binds
    fun bindEventDetailRepository(repository: EventDetailRepositoryImpl): EventDetailRepository

    @Binds
    fun bindEventCheckinRepository(repository: EventCheckinRepositoryImpl): EventCheckinRepository
}
