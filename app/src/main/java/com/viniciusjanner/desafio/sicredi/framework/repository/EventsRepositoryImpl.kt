package com.viniciusjanner.desafio.sicredi.framework.repository

import com.viniciusjanner.desafio.core.data.repository.EventsRemoteDataSource
import com.viniciusjanner.desafio.core.data.repository.EventsRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventsRemoteDataSource,
) : EventsRepository {

    override suspend fun getEvents(): List<Event> =
        remoteDataSource.fetchEvents()

    override suspend fun getEvent(eventId: String): Event =
        remoteDataSource.fetchEvent(eventId)

    override suspend fun sendEventCheckin(checkIn: EventCheckInSend): EventCheckinResponse =
        remoteDataSource.sendCheckin(checkIn)

}
