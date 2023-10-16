package com.viniciusjanner.desafio.sicredi.util.extensions

import androidx.annotation.DimenRes
import com.viniciusjanner.desafio.sicredi.framework.application.EventApp

@Suppress("unused")
fun @receiver:DimenRes Int.dp() = EventApp.instance.resources.getDimensionPixelSize(this)
