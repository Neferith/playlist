package com.nef.playlist.domain

import com.frontparissportifs.utils.DataState
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
    ):Flow<DataState<List<PlaylistEntity>>> = flow {
       try {
           if(useCache) {
               emit(DataState.Success(playlistRepository.getPlaylistFromCache()))
           } else {
               val res = playlistRepository.getPlaylistFromNetwork()
               emit(DataState.Success(res))
               playlistRepository.insertInCache(res)
           }
       } catch (e: Exception) {
           emit(DataState.Error(e))
       }
    }

}