package com.viniciusjanner.desafio.sicredi.framework.repository

import com.viniciusjanner.desafio.core.data.repository.EventDataSourceRemote
import com.viniciusjanner.desafio.core.data.repository.EventRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventDataSourceRemote,
) : EventRepository {

    override suspend fun getEvents(): List<Event> =
        remoteDataSource.getEvents()

    override suspend fun getEvent(eventId: String): Event =
        remoteDataSource.getEvent(eventId)

    override suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse =
        remoteDataSource.postEventCheckin(checkIn)

}
