package com.example.arcanittest.data

import com.example.arcanittest.data.network.service.UsersService
import com.example.arcanittest.domain.model.User
import com.example.arcanittest.domain.model.toDomain
import com.example.arcanittest.domain.repository.UsersRepository

class UsersRepositoryImpl(
    private val usersService: UsersService,
) : UsersRepository {
    override suspend fun searchUsers(query: String): List<User> {
        val apiQuery = "$query $IN_QUALIFIER"
        return usersService.searchUsers(apiQuery, 1, 30).items.map { it.toDomain() }
    }

    override suspend fun getUser(userId: Long): User = usersService.getUser(userId).toDomain()

    companion object {
        const val IN_QUALIFIER = "in:login"
    }
}