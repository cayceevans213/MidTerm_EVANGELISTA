package com.example.ktorapp.data.onlineRepository

import com.example.ktorapp.data.Repository.PhotoRepository
import com.example.ktorapp.data.Repository.PostRepository
import com.example.ktorapp.model.Photo
import com.example.ktorapp.model.Post
import com.example.ktorapp.network.HttpRoutes
import com.example.ktorapp.network.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class OnlinePhotoRepository (private val ktorClient: HttpClient = KtorClient()): PhotoRepository {

    override suspend fun getPhoto(): List<Photo> {
        val response: List<Photo> = ktorClient.request(HttpRoutes.PHOTO) {
            method = HttpMethod.Get
            url(HttpRoutes.PHOTO)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
        return response
    }
}