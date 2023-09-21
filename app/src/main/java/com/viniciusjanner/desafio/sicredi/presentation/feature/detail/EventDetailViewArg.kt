package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.os.Parcelable
import androidx.annotation.Keep
import com.viniciusjanner.desafio.sicredi.util.extensions.formatDateHour
import com.viniciusjanner.desafio.sicredi.util.extensions.formatMoney
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class EventDetailViewArg(
    val eventId: String,
    val eventImageUrl: String,
    val eventDate: Long?,
    val eventPrice: Double?,
    val eventTitle: String?,
    val eventLatitude: Double?,
    val eventLongitude: Double?,
) : Parcelable

fun EventDetailViewArg.toShareEvent(): String {
    return "Titulo: $eventTitle" +
            "\n\nData: ${eventDate?.formatDateHour()}" +
            "\n\nPreço: ${eventPrice?.formatMoney()}"
}
