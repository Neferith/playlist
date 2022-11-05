package com.nef.playlist.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlaylistCacheEntity::class], version = 1)
abstract class PlaylistDatabase : RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao

    companion object {
        const val DATABASE_NAME: String = "playlist_db"
    }

}