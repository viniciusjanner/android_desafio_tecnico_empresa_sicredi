package com.viniciusjanner.desafio.core.data.repository

import com.viniciusjanner.desafio.core.domain.model.Event

interface EventsRepository {

    suspend fun getEvents(): List<Event>
}
