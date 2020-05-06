package com.example.headspacecodechallenge.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.headspacecodechallenge.db.entities.ImageEntry

@Dao
interface ImageDao {

    @Query("SELECT * FROM imageTable")
    fun getAllImages(): Array<ImageEntry>

    @Insert(onConflict = REPLACE)
    fun insertImages(Image: ImageEntry): Long

}