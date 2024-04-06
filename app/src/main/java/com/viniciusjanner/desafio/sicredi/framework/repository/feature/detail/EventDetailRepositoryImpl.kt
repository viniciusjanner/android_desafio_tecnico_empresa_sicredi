package com.viniciusjanner.desafio.sicredi.framework.repository.feature.detail

import com.viniciusjanner.desafio.core.data.remote.EventDataSourceRemote
import com.viniciusjanner.desafio.core.data.repository.feature.detail.EventDetailRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import javax.inject.Inject

class EventDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventDataSourceRemote,
) : EventDetailRepository {

    override suspend fun getEvent(eventId: String): Event =
        remoteDataSource.getEvent(eventId)
}
