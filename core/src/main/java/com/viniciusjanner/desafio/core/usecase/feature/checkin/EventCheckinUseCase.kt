package com.viniciusjanner.desafio.core.usecase.feature.checkin

import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

interface EventCheckinUseCase {

    operator fun invoke(params: GetEventParam): Flow<ResultStatus<EventCheckinResponse>>

    data class GetEventParam(val checkinSend: EventCheckinSend)
}
