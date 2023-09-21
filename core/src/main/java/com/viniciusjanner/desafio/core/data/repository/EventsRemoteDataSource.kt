package com.viniciusjanner.desafio.core.data.repository

import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse

interface EventsRemoteDataSource {

    suspend fun fetchEvents(): List<Event>

    suspend fun fetchEvent(eventId: String): Event

    suspend fun sendCheckin(checkIn: EventCheckInSend): EventCheckinResponse
}
