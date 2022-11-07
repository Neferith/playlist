package com.nef.playlist.ui.utils

import android.view.View
import android.widget.ImageView

interface ImageLoader {
    fun loadImageInView(parent: View, image: ImageView, url:String)
}