package com.viniciusjanner.desafio.sicredi.framework.repository.feature.list

import com.viniciusjanner.desafio.core.data.remote.EventDataSourceRemote
import com.viniciusjanner.desafio.core.data.repository.feature.list.EventListRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import javax.inject.Inject

class EventListRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventDataSourceRemote,
) : EventListRepository {

    override suspend fun getEvents(): List<Event> =
        remoteDataSource.getEvents()
}
