package com.example.ktorapp.data.onlineRepository

import com.example.ktorapp.data.Repository.UserRepository
import com.example.ktorapp.model.UserProfile
import com.example.ktorapp.network.HttpRoutes
import com.example.ktorapp.network.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnlineUserRepository(private val ktorClient: HttpClient = KtorClient() ) : UserRepository {
    override fun getAllUsersStream(): Flow<List<UserProfile>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserStream(id: String): Flow<UserProfile?> {
        val cl = ktorClient.request(
            HttpRoutes.login
        ) {
            method = HttpMethod.Post
            url(HttpRoutes.login)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(formData {
                append("type", "check_login")
                append("username", id)
            }))
        }

        return flow {
            emit(cl.body())
        }
    }

    // login
    override suspend fun getUserPasswordStream(username: String, password: String): Flow<UserProfile?> {
        val cl = ktorClient.request(
            HttpRoutes.login
        ) {
            method = HttpMethod.Post
            url(HttpRoutes.login)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(formData {
                append("type", "login")
                append("username", username)
                append("password", password)
            }))
        }
        return flow {
            emit(cl.body())
        }
    }
    // end login
    override suspend fun insertUser(user: UserProfile) {
        val cl = ktorClient.request(
            HttpRoutes.login
        ) {
            method = HttpMethod.Post
            url(HttpRoutes.login)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(formData {
                append("type", "save_user")
                append("username", user.username)
                append("password", user.password)
                append("firstName", user.firstName)
                append("lastName", user.lastName)
            }))
        }
    }
    override suspend fun deleteUser(user: UserProfile) { TODO("Not yet implemented") }
    override suspend fun updateUser(user: UserProfile) { TODO("Not yet implemented") }
}
