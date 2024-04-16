package com.example.ktorapp.data.offlineRepository

import com.example.ktorapp.data.Repository.PhotoRepository
import com.example.ktorapp.data.Repository.PostRepository
import com.example.ktorapp.data.dao.PhotoDao
import com.example.ktorapp.data.dao.PostDao
import com.example.ktorapp.model.Photo
import com.example.ktorapp.model.Post

class OfflinePhotoRepository(private val photoDao: PhotoDao) : PhotoRepository {
    override suspend fun getPhoto(): List<Photo> {
        return photoDao.getAllPhoto()
    }

}