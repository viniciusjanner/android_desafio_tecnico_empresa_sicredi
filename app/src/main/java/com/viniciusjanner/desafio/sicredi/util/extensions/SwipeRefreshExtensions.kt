package com.viniciusjanner.desafio.sicredi.util.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@Suppress("unused")
fun SwipeRefreshLayout.show() {
    isRefreshing = true
}

@Suppress("unused")
fun SwipeRefreshLayout.hide() {
    isRefreshing = false
}
