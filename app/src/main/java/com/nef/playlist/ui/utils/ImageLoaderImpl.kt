package com.nef.playlist.ui.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import javax.inject.Inject

class ImageLoaderImpl @Inject constructor():ImageLoader {


    fun generateGlideUrl(url:String):GlideUrl {
        return  GlideUrl(
            url+".JPG", LazyHeaders.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .build()
        )
    }

    override fun loadImageInView(parent:View, image:ImageView, url:String) {

        val url = generateGlideUrl(url)

        Glide.with(parent).load(url)
            .placeholder(com.google.android.material.R.color.material_grey_300)
            .error(com.google.android.material.R.color.material_grey_300)
            .into(image)
    }

}