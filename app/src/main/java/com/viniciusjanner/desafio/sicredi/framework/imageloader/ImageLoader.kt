package com.viniciusjanner.desafio.sicredi.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.viniciusjanner.desafio.sicredi.R

interface ImageLoader {

    fun load(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeholder: Int = R.drawable.ic_placeholder_image,
        @DrawableRes fallback: Int = R.drawable.ic_placeholder_error_loading,
    )
}
