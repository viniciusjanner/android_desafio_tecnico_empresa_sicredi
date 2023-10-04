package com.viniciusjanner.desafio.sicredi.util.extensions

import android.view.View

@Suppress("unused")
fun View.visibilityGone() {
    this.visibility = View.GONE
}

@Suppress("unused")
fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}

@Suppress("unused")
fun View.visibilityInvisible() {
    this.visibility = View.INVISIBLE
}
