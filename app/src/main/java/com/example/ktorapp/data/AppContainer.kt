package com.example.ktorapp.data

import android.content.Context
import com.example.ktorapp.data.Repository.PhotoRepository
import com.example.ktorapp.data.Repository.PostRepository
import com.example.ktorapp.data.Repository.UserRepository
import com.example.ktorapp.data.offlineRepository.OfflinePhotoRepository
import com.example.ktorapp.data.offlineRepository.OfflinePostRepository
import com.example.ktorapp.data.offlineRepository.OfflineUserRepository
import com.example.ktorapp.data.onlineRepository.OnlinePhotoRepository
import com.example.ktorapp.data.onlineRepository.OnlinePostRepository
import com.example.ktorapp.data.onlineRepository.OnlineUserRepository
import com.example.ktorapp.database.KtorDatabase

interface AppContainer {
    val userRepository: UserRepository
    val onlineUserRepository: UserRepository
    val onlinePostRepository: OnlinePostRepository
    val postRepository: PostRepository
    val photoRepository: PhotoRepository
    val onlinePhotoRepository: OnlinePhotoRepository


    class AppDataContainer(private val context: Context) : AppContainer {
        /**
         * Implementation for [userRepository]
         */
        override val userRepository: UserRepository by lazy {
            OfflineUserRepository(KtorDatabase.getDatabase(context).UserProfileDao())
        }

        override val postRepository: PostRepository by lazy {
            OfflinePostRepository(KtorDatabase.getDatabase(context).PostDao())
        }

        override val photoRepository: PhotoRepository by lazy {
            OfflinePhotoRepository(KtorDatabase.getDatabase(context).PhotoDao())
        }

        override val onlineUserRepository: UserRepository by lazy {
            OnlineUserRepository()
        }

        override val onlinePostRepository: OnlinePostRepository by lazy {
            OnlinePostRepository()
        }

        override val onlinePhotoRepository: OnlinePhotoRepository by lazy {
            OnlinePhotoRepository()
        }

    }
}