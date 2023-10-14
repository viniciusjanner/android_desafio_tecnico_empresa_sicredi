package com.viniciusjanner.desafio.sicredi.util.extensions

import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

//
// HorizontalScrollView
//
@Suppress("ClickableViewAccessibility", "unused")
fun HorizontalScrollView.disableScroll() {
    this.setOnTouchListener { _, _ -> true }
}

@Suppress("ClickableViewAccessibility", "unused")
fun HorizontalScrollView.enableScroll() {
    this.setOnTouchListener { _, _ -> false }
}

@Suppress("unused")
fun HorizontalScrollView.resetPositionScroll() {
    this.fling(0)
    this.post {
        this.scrollTo(0, 0)
    }
}

//
// NestedScrollView
//
@Suppress("ClickableViewAccessibility", "unused")
fun NestedScrollView.disableScroll() {
    this.setOnTouchListener { _, _ -> true }
}

@Suppress("ClickableViewAccessibility", "unused")
fun NestedScrollView.enableScroll() {
    this.setOnTouchListener { _, _ -> false }
}

@Suppress("unused")
fun NestedScrollView.resetPositionScroll() {
    this.fling(0)
    this.post {
        this.scrollTo(0, 0)
    }
}

//
// ScrollView
//
@Suppress("ClickableViewAccessibility", "unused")
fun ScrollView.disableScroll() {
    this.setOnTouchListener { _, _ -> true }
}

@Suppress("ClickableViewAccessibility", "unused")
fun ScrollView.enableScroll() {
    this.setOnTouchListener { _, _ -> false }
}

@Suppress("unused")
fun ScrollView.resetPositionScroll() {
    this.fling(0)
    this.post {
        this.scrollTo(0, 0)
    }
}
