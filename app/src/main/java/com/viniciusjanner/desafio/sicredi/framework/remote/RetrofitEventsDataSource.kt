package com.viniciusjanner.desafio.sicredi.framework.remote

import com.viniciusjanner.desafio.core.data.repository.EventsRemoteDataSource
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.sicredi.framework.network.EventApi
import com.viniciusjanner.desafio.sicredi.framework.network.response.toEventModel
import javax.inject.Inject

class RetrofitEventsDataSource @Inject constructor(
    private val eventApi: EventApi,
) : EventsRemoteDataSource {

    override suspend fun fetchEvents(): List<Event> {
        return eventApi
            .getEvents().map {
                it.toEventModel()
            }
    }

    override suspend fun fetchEvent(eventId: String): Event {
        return eventApi.getEvent(eventId).toEventModel()
    }

    override suspend fun sendCheckin(checkIn: EventCheckinSend): EventCheckinResponse {
        return eventApi.postCheckin(checkIn)
    }
}
