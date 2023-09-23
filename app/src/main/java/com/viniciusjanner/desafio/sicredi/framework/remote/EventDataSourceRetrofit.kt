package com.viniciusjanner.desafio.sicredi.framework.remote

import com.viniciusjanner.desafio.core.data.repository.EventDataSourceRemote
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.sicredi.framework.network.EventApi
import com.viniciusjanner.desafio.sicredi.framework.network.response.toEventModel
import javax.inject.Inject

class EventDataSourceRetrofit @Inject constructor(
    private val eventApi: EventApi,
) : EventDataSourceRemote {

    override suspend fun getEvents(): List<Event> {
        return eventApi
            .getEvents().map {
                it.toEventModel()
            }
    }

    override suspend fun getEvent(eventId: String): Event {
        return eventApi.getEvent(eventId).toEventModel()
    }

    override suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse {
        return eventApi.postEventCheckin(checkIn)
    }
}
