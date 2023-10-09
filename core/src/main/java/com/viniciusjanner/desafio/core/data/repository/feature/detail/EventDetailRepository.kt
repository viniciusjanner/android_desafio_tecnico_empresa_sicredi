package com.viniciusjanner.desafio.core.data.repository.feature.detail

import com.viniciusjanner.desafio.core.domain.model.Event

interface EventDetailRepository {

    suspend fun getEvent(eventId: String): Event
}
