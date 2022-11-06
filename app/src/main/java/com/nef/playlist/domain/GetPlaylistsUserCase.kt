package com.nef.playlist.domain

import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlaylistsUserCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    suspend fun invoke(
        useCache:Boolean
    ):Flow<List<PlaylistEntity>> = flow {
        if(useCache) {
            emit(playlistRepository.getPlaylistFromCache())
        } else {
            val res = playlistRepository.getPlaylistFromNetwork()
            emit(res)
            playlistRepository.insertInCache(res)
        }
    }

}