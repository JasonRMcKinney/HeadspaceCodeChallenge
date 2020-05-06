package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.dao.ImageDao
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageResponse
import com.example.headspacecodechallenge.network.WebService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageRepositoryImpl(private val imageDao: ImageDao, private val webService: WebService) :
    ImageRepository {

    override fun allImages(): Array<ImageEntry> {
        return imageDao.getAllImages()
    }

    override fun insertImage(image: ImageEntry) {
        imageDao.insertImages(image)
    }

    override fun webImages(): Single<ImageResponse> {
        return WebService.instance.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}