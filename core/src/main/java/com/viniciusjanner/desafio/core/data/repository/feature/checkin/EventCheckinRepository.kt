package com.viniciusjanner.desafio.core.data.repository.feature.checkin

import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend

interface EventCheckinRepository {

    suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse
}
