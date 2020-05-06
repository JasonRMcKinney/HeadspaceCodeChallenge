package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageItem
import io.reactivex.Single

interface ImageRepository {
    fun allImages(): List<ImageEntry>

    fun insertImage(image: ImageEntry)

    fun webImages(): Single<Array<ImageItem>>
}