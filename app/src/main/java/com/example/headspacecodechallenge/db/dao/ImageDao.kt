package com.example.headspacecodechallenge.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.headspacecodechallenge.db.entities.ImageEntry
import io.reactivex.Completable

@Dao
interface ImageDao {

    @Query("SELECT * FROM imageTable")
    fun getAllImages(): List<ImageEntry>

    @Insert(onConflict = IGNORE)
    fun insertImages(image: ImageEntry): Completable

}