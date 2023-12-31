package com.viniciusjanner.desafio.core.usecase

import com.viniciusjanner.desafio.core.data.repository.EventRepository
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface EventCheckinUseCase {
    //
    // operator : nos permite suprimir o .invoke na chamada do método.
    //
    operator fun invoke(params: GetEventParam): Flow<ResultStatus<EventCheckinResponse>>

    data class GetEventParam(val checkinSend: EventCheckinSend)
}

class EventCheckinUseCaseImpl @Inject constructor(
    private val repository: EventRepository,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : UseCase<EventCheckinUseCase.GetEventParam, EventCheckinResponse>(),
    EventCheckinUseCase {

    override suspend fun doWork(params: EventCheckinUseCase.GetEventParam): ResultStatus<EventCheckinResponse> {
        return withContext(coroutinesDispatchers.io()) {
            ResultStatus.Success(repository.postEventCheckin(params.checkinSend))
        }
    }
}
