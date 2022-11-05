package com.nef.playlist.data.dao

import com.frontparissportifs.utils.EntityMapper
import com.nef.playlist.data.model.PlaylistEntity
import javax.inject.Inject

class PlaylistCacheMapper @Inject
constructor() : EntityMapper<PlaylistCacheEntity, PlaylistEntity> {

    override fun mapFromEntity(entity: PlaylistCacheEntity): PlaylistEntity {
        return PlaylistEntity(
            id = entity.id,
            albumId = entity.albumId,
            title = entity.title,
            url = entity.url,
            thumbnail = entity.thumbnail
        )
    }

    override fun mapToEntity(domainModel: PlaylistEntity): PlaylistCacheEntity {
        return PlaylistCacheEntity(
            id = domainModel.id,
            albumId = domainModel.albumId,
            title = domainModel.title,
            url = domainModel.url,
            thumbnail = domainModel.thumbnail
        )
    }

    fun mapFromEntityList(entities: List<PlaylistCacheEntity>): List<PlaylistEntity> {
        return entities.map { mapFromEntity(it) }
    }

}