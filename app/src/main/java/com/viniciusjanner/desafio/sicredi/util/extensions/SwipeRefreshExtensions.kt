package com.viniciusjanner.desafio.sicredi.util.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Suppress("unused")
fun SwipeRefreshLayout.disable() {
    this.isEnabled = false
}

@Suppress("unused")
fun SwipeRefreshLayout.enable() {
    this.isEnabled = true
}

@Suppress("unused")
fun SwipeRefreshLayout.show() {
    this.isRefreshing = true
}

@Suppress("unused")
fun SwipeRefreshLayout.hide() {
    this.isRefreshing = false
}
