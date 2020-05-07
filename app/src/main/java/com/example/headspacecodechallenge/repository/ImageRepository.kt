package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageItem

interface ImageRepository {
    suspend fun allImages(): List<ImageEntry>

    suspend fun insertImage(image: ImageEntry)

    suspend fun webImages(): Array<ImageItem>
}