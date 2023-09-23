package com.viniciusjanner.desafio.testing.model

import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse

class EventCheckinFactory {

    fun checkinSend(): EventCheckinSend =
        EventCheckinSend(
            eventId = "1",
            name = "Vinicius",
            email = "vinicius@gmail.com"
        )

    fun checkinResponse(): EventCheckinResponse =
        EventCheckinResponse(
            code = 200
        )
}
