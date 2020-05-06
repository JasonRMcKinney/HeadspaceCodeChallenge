package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.dao.ImageDao
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageResponseItem
import com.example.headspacecodechallenge.network.WebService

class ImageRepositoryImpl(private val imageDao: ImageDao, private val webService: WebService) :
    ImageRepository {

    override fun allImages(): Array<ImageEntry> {
        return imageDao.getAllImages()
    }

    override fun insertImage(image: ImageEntry) {
        imageDao.insertImages(image)
    }

    override fun webImages(): List<ImageResponseItem> {
        return WebService.instance.getImages()
    }


}