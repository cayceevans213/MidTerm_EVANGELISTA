package com.example.ktorapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
data class LoginRequest( val username: String, val password: String )
data class LoginResponse( val username: String, val password: String )
@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
@Entity(tableName = "user")
data class UserProfile(
    @PrimaryKey val username: String,
    val password: String,
    val firstName: String,
    val lastName: String

)