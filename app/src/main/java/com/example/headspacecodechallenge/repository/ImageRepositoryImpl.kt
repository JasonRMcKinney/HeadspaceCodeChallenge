package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.ImageDatabase
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageItem
import com.example.headspacecodechallenge.network.WebService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageRepositoryImpl(
    private val database: ImageDatabase?,
    private val webService: WebService
) :
    ImageRepository {

    override fun allImages(): List<ImageEntry> {
        if (database != null) {
            return database.imageDao().getAllImages()
        }
        return emptyList()
    }

    override fun insertImage(image: ImageEntry) {
        database?.imageDao()?.insertImages(image)
    }

    override fun webImages(): Single<Array<ImageItem>> {
        return WebService.instance.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}