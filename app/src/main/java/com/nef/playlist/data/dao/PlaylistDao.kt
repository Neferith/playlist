package com.nef.playlist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teamEntity: PlaylistCacheEntity): Long

    @Query("SELECT * FROM playlists")
     suspend fun getAll(): List<PlaylistCacheEntity>

}