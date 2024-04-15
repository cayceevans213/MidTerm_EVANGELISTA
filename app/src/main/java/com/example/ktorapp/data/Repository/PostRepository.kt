package com.example.ktorapp.data.Repository

import com.example.ktorapp.model.Post

interface PostRepository {

    // Retrieve all posts
    suspend fun getPosts(): List<Post>
}