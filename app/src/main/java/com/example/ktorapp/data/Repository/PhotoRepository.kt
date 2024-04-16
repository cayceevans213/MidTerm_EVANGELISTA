package com.example.ktorapp.data.Repository

import com.example.ktorapp.model.Photo
import com.example.ktorapp.model.Post

interface PhotoRepository {

    // Retrieve all posts
    suspend fun getPhoto(): List<Photo>

}