package com.example.ktorapp.data.offlineRepository

import com.example.ktorapp.data.Repository.PostRepository
import com.example.ktorapp.data.dao.PostDao
import com.example.ktorapp.model.Post

class OfflinePostRepository(private val postDao: PostDao) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postDao.getAllPosts()
    }

}