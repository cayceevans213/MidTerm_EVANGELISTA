package com.example.ktorapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ktorapp.data.Repository.PhotoRepository
import com.example.ktorapp.data.Repository.PostRepository
import com.example.ktorapp.model.Photo
import com.example.ktorapp.model.Post
import timber.log.Timber

class PhotoViewModel(private val photoRepository: PhotoRepository): ViewModel() {
    var photoUiState by mutableStateOf(PhotosUiState())

    suspend fun getPhoto() {
        val photo = photoRepository.getPhoto()
        photoUiState =
            PhotosUiState(photo = photo)
        Timber.i("postsUiState $photoUiState")
    }

    data class PhotosUiState(
        var photo: List<Photo> = emptyList()
    )
}