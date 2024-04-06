package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class EventDetailArgs(
    val eventId: String,
) : Parcelable
