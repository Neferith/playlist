package com.nef.playlist.ui.main

import androidx.lifecycle.*
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.domain.CoroutineDispatcherProvider
import com.nef.playlist.domain.GetPlaylistsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MainViewModel @Inject constructor(private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
                                        val getProjectsUseCase: GetPlaylistsUserCase) : ViewModel() {


    val playlistLiveData: MutableLiveData<List<PlaylistEntity>> = MutableLiveData()
    init {

       viewModelScope.launch(coroutineDispatcherProvider.io) {

           getProjectsUseCase.invoke(false).onEach { playlistLiveData.value = it }.launchIn(
               MainScope()
           )
       }

    }

    fun syncData() {

        viewModelScope.launch(coroutineDispatcherProvider.io) {

            getProjectsUseCase.invoke(true).onEach { playlistLiveData.value = it }.launchIn(
                MainScope()
            )
        }
    }


}