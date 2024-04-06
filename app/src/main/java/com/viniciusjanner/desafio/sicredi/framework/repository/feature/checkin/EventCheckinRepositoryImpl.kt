package com.viniciusjanner.desafio.sicredi.framework.repository.feature.checkin

import com.viniciusjanner.desafio.core.data.remote.EventDataSourceRemote
import com.viniciusjanner.desafio.core.data.repository.feature.checkin.EventCheckinRepository
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import javax.inject.Inject

class EventCheckinRepositoryImpl @Inject constructor(
    private val remoteDataSource: EventDataSourceRemote,
) : EventCheckinRepository {

    override suspend fun postEventCheckin(checkIn: EventCheckinSend): EventCheckinResponse =
        remoteDataSource.postEventCheckin(checkIn)
}
