package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.ImageDatabase
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageItem
import com.example.headspacecodechallenge.network.WebService

class ImageRepositoryImpl(
    private val database: ImageDatabase?,
    private val webService: WebService
) :
    ImageRepository {

    override suspend fun allImages(): List<ImageEntry> {
        if (database != null) {
            return database.imageDao().getAllImages()
        }
        return emptyList()
    }

    override suspend fun insertImage(image: ImageEntry) {
        database?.imageDao()?.insertImages(image)
    }

    override suspend fun webImages(): Array<ImageItem> {
        return WebService.instance.getImages()
    }


}