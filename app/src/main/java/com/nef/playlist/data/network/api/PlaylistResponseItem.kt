package com.nef.playlist.data.network.api

data class PlaylistResponseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)