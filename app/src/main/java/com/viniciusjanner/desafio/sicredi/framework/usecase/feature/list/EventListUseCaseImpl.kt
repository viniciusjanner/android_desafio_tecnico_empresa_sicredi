package com.viniciusjanner.desafio.sicredi.framework.usecase.feature.list

import com.viniciusjanner.desafio.core.data.repository.feature.list.EventListRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.base.UseCase
import com.viniciusjanner.desafio.core.usecase.feature.list.EventListUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventListUseCaseImpl @Inject constructor(
    private val repository: EventListRepository,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : UseCase<Unit, List<Event>>(),
    EventListUseCase {

    override suspend fun doWork(params: Unit): ResultStatus<List<Event>> {
        return withContext(coroutinesDispatchers.io()) {
            ResultStatus.Success(repository.getEvents())
        }
    }
}
