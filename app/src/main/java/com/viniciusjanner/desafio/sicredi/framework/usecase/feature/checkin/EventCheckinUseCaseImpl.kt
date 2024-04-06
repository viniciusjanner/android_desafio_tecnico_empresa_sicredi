package com.viniciusjanner.desafio.sicredi.framework.usecase.feature.checkin

import com.viniciusjanner.desafio.core.data.repository.feature.checkin.EventCheckinRepository
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.base.UseCase
import com.viniciusjanner.desafio.core.usecase.feature.checkin.EventCheckinUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventCheckinUseCaseImpl @Inject constructor(
    private val repository: EventCheckinRepository,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : UseCase<EventCheckinUseCase.GetEventParam, EventCheckinResponse>(),
    EventCheckinUseCase {

    override suspend fun doWork(params: EventCheckinUseCase.GetEventParam): ResultStatus<EventCheckinResponse> {
        return withContext(coroutinesDispatchers.io()) {
            ResultStatus.Success(repository.postEventCheckin(params.checkinSend))
        }
    }
}
