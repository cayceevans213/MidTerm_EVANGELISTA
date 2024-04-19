package com.example.ktorapp.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ktorapp.database.KtorDatabase
import com.example.ktorapp.viewmodel.LoginScreenViewModel
import com.example.ktorapp.viewmodel.PhotoViewModel
import com.example.ktorapp.viewmodel.PostViewModel
import com.example.ktorapp.viewmodel.RegistrationScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RegistrationScreenViewModel(

                ktorApplication().container.onlineUserRepository, ktorApplication().container.offlineUserRepository
            )
        }


        initializer {
            LoginScreenViewModel(
                ktorApplication().container.onlineUserRepository,   ktorApplication().container.offlineUserRepository
            )
        }


        initializer {
            PostViewModel(ktorApplication().container.onlinePostRepository)
        }

        initializer {
            PhotoViewModel(ktorApplication().container.onlinePhotoRepository)
        }


    }
}
fun CreationExtras.ktorApplication(): KtorApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KtorApp)