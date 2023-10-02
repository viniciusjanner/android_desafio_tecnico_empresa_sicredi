package com.viniciusjanner.desafio.sicredi.util.extensions

import java.text.NumberFormat

@Suppress("unused")
fun Double.formatMoney() = NumberFormat.getCurrencyInstance().format(this) ?: ""
