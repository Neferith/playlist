package com.nef.playlist.di

import android.content.Context
import androidx.room.Room
import com.nef.playlist.data.dao.PlaylistDao
import com.nef.playlist.data.dao.PlaylistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTeamDb(@ApplicationContext context: Context): PlaylistDatabase {
        return Room.databaseBuilder(
            context, PlaylistDatabase::class.java,
            PlaylistDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePlaylistDAO(playlistDatabase: PlaylistDatabase): PlaylistDao {
        return playlistDatabase.playlistDao()
    }

}