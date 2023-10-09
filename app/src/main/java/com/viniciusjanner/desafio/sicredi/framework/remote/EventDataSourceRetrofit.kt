package com.viniciusjanner.desafio.sicredi.framework.remote

import com.viniciusjanner.desafio.core.data.remote.EventDataSourceRemote
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.sicredi.framework.network.response.toEventModel
import com.viniciusjanner.desafio.sicredi.framework.network.service.EventApi
import javax.inject.Inject

class EventDataSourceRetrofit @Inject constructor(
    private val eventApi: EventApi,
) : EventDataSourceRemote {

    override suspend fun getEvents(): List<Event> =
        eventApi.getEvents().map {
            it.toEventModel()
        }

    override suspend fun getEvent(eventId: String): Event =
        eventApi.getEvent(eventId).toEventModel()

    override suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse =
        eventApi.postEventCheckin(checkIn)
}
