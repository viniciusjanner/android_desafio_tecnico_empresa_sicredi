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

//
// TODO: Vinicius Janner
//  Mock para quando a API sair do ar e deixar de retornar os dados.
//
//
//    override suspend fun getEvents(): List<Event> {
//        delay(2500)
//        return listOf(
//            EventFactory().create(EventFactory.EventFake.Event4),
//            EventFactory().create(EventFactory.EventFake.Event1),
//            EventFactory().create(EventFactory.EventFake.Event5),
//
//            EventFactory().create(EventFactory.EventFake.Event4),
//            EventFactory().create(EventFactory.EventFake.Event1),
//            EventFactory().create(EventFactory.EventFake.Event5),
//        )
//    }
//
//    override suspend fun getEvent(eventId: String): Event {
//        delay(2500)
//        return when (eventId) {
//            "1" -> EventFactory().create(EventFactory.EventFake.Event1)
//            "2" -> EventFactory().create(EventFactory.EventFake.Event2)
//            "3" -> EventFactory().create(EventFactory.EventFake.Event3)
//            "4" -> EventFactory().create(EventFactory.EventFake.Event4)
//            "5" -> EventFactory().create(EventFactory.EventFake.Event5)
//            else -> EventFactory().create(EventFactory.EventFake.Event5)
//        }
//    }
//
//    override suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse {
//        delay(2500)
//        return EventCheckinResponse(
//            code = 200
//        )
//    }
//
}
