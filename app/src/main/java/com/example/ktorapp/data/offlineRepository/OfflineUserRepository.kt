package com.example.ktorapp.data.offlineRepository

import com.example.ktorapp.data.Repository.UserRepository
import com.example.ktorapp.data.dao.UserProfileDao
import com.example.ktorapp.model.UserProfile
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserProfileDao) : UserRepository {
    override fun getAllUsersStream(): Flow<List<UserProfile>> = userDao.getAllUsers()
    override suspend fun getUserStream(id: String): Flow<UserProfile?> = userDao.getUsers(id)
    override suspend fun getUserPasswordStream(username: String, password: String): Flow<UserProfile?> = userDao.getUsersPass(username, password)
    override suspend fun insertUser(user: UserProfile) = userDao.insert(user)
    override suspend fun deleteUser(user: UserProfile) = userDao.delete(user)
    override suspend fun updateUser(user: UserProfile) = userDao.update(user)

}