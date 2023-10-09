package com.viniciusjanner.desafio.sicredi.util.extensions

import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout

@Suppress("unused")
fun ShimmerFrameLayout.show() {
    isVisible = true
    startShimmer()
}

@Suppress("unused")
fun ShimmerFrameLayout.hide() {
    isVisible = false
    stopShimmer()
}
