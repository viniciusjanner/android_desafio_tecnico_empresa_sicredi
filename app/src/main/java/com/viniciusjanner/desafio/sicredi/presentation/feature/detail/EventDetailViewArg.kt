package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class EventDetailViewArg(
    val eventId: String,
    val eventImageUrl: String,
) : Parcelable
