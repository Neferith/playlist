package com.nef.playlist.domain

import com.nef.playlist.utils.DataState
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.data.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlaylistsUserCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    suspend fun invoke(
        useCache: Boolean
    ): Flow<DataState<List<PlaylistEntity>>> = flow {
        emit(DataState.Loading)
        if (useCache) {
            emit(DataState.Success(playlistRepository.getPlaylistFromCache()))
        } else {
            val res = playlistRepository.getPlaylistFromNetwork()
            emit(DataState.Success(res))
            playlistRepository.insertInCache(res)
        }
    }.catch { e ->

        emit(DataState.Error(e))

    }

}