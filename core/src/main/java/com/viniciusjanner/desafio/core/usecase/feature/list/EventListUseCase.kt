package com.viniciusjanner.desafio.core.usecase.feature.list

import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

interface EventListUseCase {

    operator fun invoke(params: Unit = Unit): Flow<ResultStatus<List<Event>>>
}
