package com.viniciusjanner.desafio.sicredi.util.extensions

import java.text.NumberFormat

fun Double.formatMoney() = NumberFormat.getCurrencyInstance().format(this) ?: ""
