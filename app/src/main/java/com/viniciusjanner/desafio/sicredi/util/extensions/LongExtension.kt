package com.viniciusjanner.desafio.sicredi.util.extensions

import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused")
fun Long.formatDateHour(): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy HH:mm", Locale("pt", "BR"))
    val date = Date(this)
    return dateFormat.format(date).replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ROOT)else it.toString()
    }
}
