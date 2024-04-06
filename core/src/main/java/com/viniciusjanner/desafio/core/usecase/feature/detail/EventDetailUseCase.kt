package com.viniciusjanner.desafio.core.usecase.feature.detail

import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

interface EventDetailUseCase {

    operator fun invoke(params: GetEventParam): Flow<ResultStatus<Event>>

    data class GetEventParam(val eventId: String)
}
