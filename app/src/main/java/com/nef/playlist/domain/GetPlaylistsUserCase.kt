package com.nef.playlist.domain

import android.database.sqlite.SQLiteException
import com.nef.playlist.data.dao.PlaylistCacheMapper
import com.nef.playlist.data.dao.PlaylistDao
import com.nef.playlist.data.network.api.PlaylistApi
import com.nef.playlist.data.network.api.PlaylistMapper
import com.nef.playlist.data.network.hasNetwork
import com.nef.playlist.data.repository.PlaylistRepository
import com.nef.playlist.model.PlaylistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlaylistsUserCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend fun invoke(useCache:Boolean):Flow<List<PlaylistEntity>> = flow {
        if(!useCache) {
            emit(playlistRepository.getPlaylistFromCache())
        } else {
            emit(playlistRepository.getPlaylistFromNetwork())
        }

    }
}