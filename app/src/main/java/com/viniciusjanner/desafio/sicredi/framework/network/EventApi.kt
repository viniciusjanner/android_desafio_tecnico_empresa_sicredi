package com.viniciusjanner.desafio.sicredi.framework.network

import com.viniciusjanner.desafio.sicredi.framework.network.response.EventsResponse
import retrofit2.http.GET

interface EventApi {

    @GET("api/events")
    suspend fun getEvents(): EventsResponse
}
