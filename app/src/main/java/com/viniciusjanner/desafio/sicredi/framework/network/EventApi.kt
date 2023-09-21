package com.viniciusjanner.desafio.sicredi.framework.network

import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.sicredi.framework.network.response.EventResponse
import com.viniciusjanner.desafio.sicredi.framework.network.response.EventsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {

    @GET("api/events")
    suspend fun getEvents(): EventsResponse

    @GET("/events/{id}")
    suspend fun getEvent(@Path("id") id: String): EventResponse

    @POST("/checkin")
    suspend fun postCheckIn(@Body checkIn: EventCheckInSend): EventCheckinResponse
}
