package com.viniciusjanner.desafio.core.usecase

import com.viniciusjanner.desafio.core.data.repository.EventsRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface EventsUseCase {
    //
    // operator : nos permite suprimir o .invoke na chamada do método.
    //
    operator fun invoke(params: Unit = Unit): Flow<ResultStatus<List<Event>>>
}

class EventsUseCaseImpl @Inject constructor(
    private val repository: EventsRepository,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : UseCase<Unit, List<Event>>(),
    EventsUseCase {

    override suspend fun doWork(params: Unit): ResultStatus<List<Event>> {
        return withContext(coroutinesDispatchers.io()) {
            ResultStatus.Success(repository.getEvents())
        }
    }
}
