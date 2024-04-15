package com.example.ktorapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "post")
data class Post(
    val userId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val body: String
)