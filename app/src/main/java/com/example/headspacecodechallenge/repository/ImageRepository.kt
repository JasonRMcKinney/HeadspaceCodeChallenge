package com.example.headspacecodechallenge.repository

import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.model.ImageResponseItem

interface ImageRepository {
    fun allImages(): Array<ImageEntry>

    fun insertImage(image: ImageEntry)

    fun webImages(): List<ImageResponseItem>
}