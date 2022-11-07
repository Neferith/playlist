package com.nef.playlist.di

import com.nef.playlist.ui.utils.ImageLoader
import com.nef.playlist.ui.utils.ImageLoaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class UIModule {

    @Provides
    fun provideImageLoader(imageLoader: ImageLoaderImpl): ImageLoader {
        return imageLoader
    }
}