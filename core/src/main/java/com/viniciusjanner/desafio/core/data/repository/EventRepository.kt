package com.viniciusjanner.desafio.core.data.repository

import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse

interface EventRepository {

    suspend fun getEvents(): List<Event>

    suspend fun getEvent(eventId: String): Event

    suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse
}
