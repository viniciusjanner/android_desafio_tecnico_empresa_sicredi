package com.viniciusjanner.desafio.testing.core.domain.model

import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend

class EventCheckinSendFactory {

    fun create(): EventCheckinSend =
        EventCheckinSend(
            eventId = "1",
            name = "Vinicius",
            email = "vinicius@gmail.com"
        )
}
