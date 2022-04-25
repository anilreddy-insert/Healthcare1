package com.startupai.healthcare

import androidx.annotation.DrawableRes
import com.ouattararomuald.slider.ImageLoader

class PicassoImageLoaderFactory(
    @DrawableRes private val errorResId: Int = 0,
    @DrawableRes private val placeholderResId: Int = 0,
    private val eventListener: ImageLoader.EventListener? = null
) : ImageLoader.Factory<PicassoImageLoader> {

    override fun create(): PicassoImageLoader = PicassoImageLoader(errorResId, placeholderResId, eventListener)
}