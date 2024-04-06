package com.viniciusjanner.desafio.sicredi.framework.imageloader

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.viniciusjanner.desafio.sicredi.util.extensions.replaceFromHttpToHttps
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader {

    private val tag: String = GlideImageLoader::class.java.simpleName

    override fun load(imageView: ImageView, imageUrl: String?, placeholder: Int, fallback: Int, error: Int) {
        GlideApp.with(imageView.rootView)
            .load(imageUrl?.replaceFromHttpToHttps())
            .placeholder(placeholder)
            .fallback(fallback)
            .error(error)
            .transition(DrawableTransitionOptions.withCrossFade(350))
            .listener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Log.e(tag, "onLoadFailed : GlideException : ${e?.message}")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Log.i(tag, "onResourceReady...")
                        return false
                    }
                }
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}
