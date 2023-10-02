package com.viniciusjanner.desafio.core.data.repository.feature.list

import com.viniciusjanner.desafio.core.domain.model.Event

interface EventListRepository {

    suspend fun getEvents(): List<Event>
}
