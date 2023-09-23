package com.viniciusjanner.desafio.core.usecase

import com.viniciusjanner.desafio.core.data.repository.EventRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.EventDetailUseCase.*
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface EventDetailUseCase {
    //
    // operator : nos permite suprimir o .invoke na chamada do método.
    //
    operator fun invoke(params: GetEventParam): Flow<ResultStatus<Event>>

    data class GetEventParam(val eventId: String)
}

class EventDetailUseCaseImpl @Inject constructor(
    private val repository: EventRepository,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : UseCase<GetEventParam, Event>(),
    EventDetailUseCase {

    override suspend fun doWork(params: GetEventParam): ResultStatus<Event> {
        return withContext(coroutinesDispatchers.io()) {
            ResultStatus.Success(repository.getEvent(params.eventId))
        }
    }
}
