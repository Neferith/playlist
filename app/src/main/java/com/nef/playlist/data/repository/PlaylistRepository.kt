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

    suspend fun getPlaylistFromNetwork():List<PlaylistEntity> {
        val playlistApi = playlistApi.getAll()
        val convertApiPlaylists = playlistMapper.mapFromEntityList(playlistApi)
        for (playlist in convertApiPlaylists) {
            playlistDao.insert(cacheMapper.mapToEntity(playlist))
        }
        return convertApiPlaylists

    }

    suspend fun getPlaylistFromCache():List<PlaylistEntity> {
        val playlistCache = playlistDao.getAll()
        val convertCachePlaylists = cacheMapper.mapFromEntityList(playlistCache)
        return convertCachePlaylists

    }
}