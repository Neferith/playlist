package com.nef.playlist.data.network.api

import retrofit2.http.GET

interface PlaylistApi {

    @GET("technical-test.json")
    suspend fun getAll(): List<PlaylistResponseItem>

}