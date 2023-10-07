package com.viniciusjanner.desafio.sicredi.util.extensions

import java.text.NumberFormat
import java.util.Locale

@Suppress("unused")
fun Double.formatMoneyBrazil(): String =
    NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(this)
