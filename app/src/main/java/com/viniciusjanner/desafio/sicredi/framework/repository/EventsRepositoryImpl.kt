package com.viniciusjanner.desafio.sicredi.framework.repository

import com.viniciusjanner.desafio.core.data.repository.EventsRemoteDataSource
import com.viniciusjanner.desafio.core.data.repository.EventsRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventsRemoteDataSource,
) : EventsRepository {

    override suspend fun getEvents(): List<Event> =
        remoteDataSource.fetchEvents()
}
