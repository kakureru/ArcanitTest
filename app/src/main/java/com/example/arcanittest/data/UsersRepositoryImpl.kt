package com.example.arcanittest.data

import com.example.arcanittest.data.network.service.UsersService
import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.model.toDomain
import com.example.arcanittest.domain.repository.UsersRepository

class UsersRepositoryImpl(
    private val usersService: UsersService,
) : UsersRepository {
    override suspend fun searchUsers(query: String): List<User> {
        val apiQuery = "$query $QUALIFIER"
        return usersService.searchUsers(apiQuery, 1, 30).items.map { it.toDomain() }
    }

    companion object {
        const val QUALIFIER = "in:login"
    }
}