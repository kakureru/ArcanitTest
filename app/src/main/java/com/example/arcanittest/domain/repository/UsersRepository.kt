package com.example.arcanittest.domain.repository

import com.example.arcanittest.domain.model.User

interface UsersRepository {
    fun getUsers(): List<User>
}