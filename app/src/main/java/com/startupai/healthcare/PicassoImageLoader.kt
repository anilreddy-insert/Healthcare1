package com.startupai.healthcare

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.ouattararomuald.slider.ImageLoader
import com.squareup.picasso.Picasso

class PicassoImageLoader(
    @DrawableRes private val errorResId: Int,
    @DrawableRes private val placeholderResId: Int,
    eventListener: EventListener? = null
) : ImageLoader(eventListener) {

    override fun load(path: String, imageView: ImageView) {
        Picasso.get().load(path).apply {
            if (placeholderResId > 0) {
                placeholder(placeholderResId)
            }
            if (errorResId > 0) {
                error(errorResId)
            }
            fit()
            into(imageView)
        }
    }
}