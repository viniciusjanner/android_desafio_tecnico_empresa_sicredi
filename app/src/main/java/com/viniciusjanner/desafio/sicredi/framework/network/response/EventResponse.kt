package com.viniciusjanner.desafio.sicredi.framework.network.response

import com.google.gson.annotations.SerializedName
import com.viniciusjanner.desafio.core.domain.model.Event

data class EventResponse(
    @SerializedName("people")
    val people: List<Any?>?,
    @SerializedName("date")
    val date: Long?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("id")
    val id: String,
)

fun EventResponse.toEventModel(): Event {
    return Event(
        people = this.people,
        date = this.date,
        description = this.description,
        image = this.image,
        longitude = this.longitude,
        latitude = this.latitude,
        price = this.price,
        title = this.title,
        id = this.id,
    )
}
