package com.nef.playlist.data.network.api

import com.frontparissportifs.utils.EntityMapper
import com.nef.playlist.data.model.PlaylistEntity
import javax.inject.Inject

class PlaylistMapper @Inject
constructor() : EntityMapper<PlaylistResponseItem, PlaylistEntity> {

    override fun mapFromEntity(entity: PlaylistResponseItem): PlaylistEntity {
        return PlaylistEntity(
            id = entity.id,
            albumId = entity.albumId,
            title = entity.title,
            url = entity.url,
            thumbnail = entity.thumbnailUrl
        )
    }

    override fun mapToEntity(domainModel: PlaylistEntity): PlaylistResponseItem {
        return PlaylistResponseItem(
            id = domainModel.id,
            albumId = domainModel.albumId,
            title = domainModel.title,
            url = domainModel.url,
            thumbnailUrl = domainModel.thumbnail
        )
    }

    fun mapFromEntityList(entities: List<PlaylistResponseItem>): List<PlaylistEntity> {
        return entities.map { mapFromEntity(it) }
    }

}