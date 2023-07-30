package com.example.arcanittest.domain.repository

import com.example.arcanittest.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getSearchResultStream(): Flow<List<User>>
    suspend fun requestMore(query: String, pageSize: Int)
    suspend fun getUser(userId: Long): User
}