package com.nef.playlist.data.repository

import com.nef.playlist.data.dao.PlaylistCacheMapper
import com.nef.playlist.data.dao.PlaylistDao
import com.nef.playlist.data.network.api.PlaylistApi
import com.nef.playlist.data.network.api.PlaylistMapper
import com.nef.playlist.data.model.PlaylistEntity
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val cacheMapper: PlaylistCacheMapper,
    private val playlistApi: PlaylistApi,
    private val playlistMapper: PlaylistMapper
) {

    suspend fun getPlaylistFromNetwork(): List<PlaylistEntity> {
        val playlistApi = playlistApi.getAll()
        return playlistMapper.mapFromEntityList(playlistApi)

    }

    suspend fun insertInCache(playlists:List<PlaylistEntity> ) {
        for (playlist in playlists) {
            playlistDao.insert(cacheMapper.mapToEntity(playlist))
        }
    }

    suspend fun getPlaylistFromCache(): List<PlaylistEntity> {
        val playlistCache = playlistDao.getAll()
        return cacheMapper.mapFromEntityList(playlistCache)
    }

}