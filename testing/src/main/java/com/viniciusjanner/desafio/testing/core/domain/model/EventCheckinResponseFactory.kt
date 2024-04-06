package com.viniciusjanner.desafio.testing.core.domain.model

import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse

class EventCheckinResponseFactory {

    fun create(): EventCheckinResponse =
        EventCheckinResponse(
            code = 200
        )
}
