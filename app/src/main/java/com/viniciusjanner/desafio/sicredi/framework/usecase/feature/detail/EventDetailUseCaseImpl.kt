package com.viniciusjanner.desafio.sicredi.framework.usecase.feature.detail

import com.viniciusjanner.desafio.core.data.repository.feature.detail.EventDetailRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.base.UseCase
import com.viniciusjanner.desafio.core.usecase.feature.detail.EventDetailUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventDetailUseCaseImpl @Inject constructor(
    private val repository: EventDetailRepository,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : UseCase<EventDetailUseCase.GetEventParam, Event>(),
    EventDetailUseCase {

    override suspend fun doWork(params: EventDetailUseCase.GetEventParam): ResultStatus<Event> {
        return withContext(coroutinesDispatchers.io()) {
            ResultStatus.Success(repository.getEvent(params.eventId))
        }
    }
}
