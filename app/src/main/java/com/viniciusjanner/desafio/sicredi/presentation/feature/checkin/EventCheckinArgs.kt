package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class EventCheckinArgs(
    val eventId: String,
) : Parcelable
