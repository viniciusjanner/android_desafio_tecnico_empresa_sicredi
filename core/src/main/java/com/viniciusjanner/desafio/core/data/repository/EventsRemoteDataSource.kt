package com.viniciusjanner.desafio.core.data.repository

import com.viniciusjanner.desafio.core.domain.model.Event

interface EventsRemoteDataSource {

    suspend fun fetchEvents(): List<Event>

    suspend fun fetchEvent(eventId: String): Event
}
