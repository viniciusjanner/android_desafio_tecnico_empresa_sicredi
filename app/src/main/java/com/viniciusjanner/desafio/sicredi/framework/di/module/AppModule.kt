package com.viniciusjanner.desafio.sicredi.framework.di.module

import com.viniciusjanner.desafio.sicredi.framework.imageloader.GlideImageLoader
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Suppress("unused")
@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}
