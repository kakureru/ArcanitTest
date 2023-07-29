package com.example.arcanittest.domain.repository

import com.example.arcanittest.domain.model.User

interface UsersRepository {
    suspend fun searchUsers(query: String): List<User>
}