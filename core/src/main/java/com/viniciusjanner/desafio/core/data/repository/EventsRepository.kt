package com.viniciusjanner.desafio.core.data.repository

import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse

interface EventsRepository {

    suspend fun getEvents(): List<Event>

    suspend fun getEvent(eventId: String): Event

    suspend fun sendEventCheckin(checkIn: EventCheckInSend): EventCheckinResponse
}
