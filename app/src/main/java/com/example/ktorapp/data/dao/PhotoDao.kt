package com.example.ktorapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ktorapp.model.Photo
import com.example.ktorapp.model.Post

@Dao
interface PhotoDao {
    @Query("SELECT * from photo ORDER BY id ASC")
    fun getAllPhoto(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Update
    fun updatePhoto(photo: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}