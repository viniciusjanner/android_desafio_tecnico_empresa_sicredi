package com.viniciusjanner.desafio.sicredi.util.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.viniciusjanner.desafio.sicredi.R

private val navOptionsFromRightToLeft = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

@Suppress("unused")
fun NavController.navigateFromRightToLeft(@IdRes destinationId: Int, args: Bundle? = null) {
    this.navigate(destinationId, args, navOptionsFromRightToLeft)
}

@Suppress("unused")
fun NavController.navigateFromRightToLeft(directions: NavDirections) {
    this.navigate(directions, navOptionsFromRightToLeft)
}

private val navOptionsFromBottomToTop = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_bottom)
    .setExitAnim(R.anim.slide_out_top)
    .setPopEnterAnim(R.anim.slide_in_top)
    .setPopExitAnim(R.anim.slide_out_bottom)
    .build()

@Suppress("unused")
fun NavController.navigateFromBottomToTop(@IdRes destinationId: Int, args: Bundle? = null) {
    this.navigate(destinationId, args, navOptionsFromBottomToTop)
}

@Suppress("unused")
fun NavController.navigateFromBottomToTop(directions: NavDirections) {
    this.navigate(directions, navOptionsFromBottomToTop)
}
