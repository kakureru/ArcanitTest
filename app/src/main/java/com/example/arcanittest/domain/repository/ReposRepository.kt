package com.example.arcanittest.domain.repository

import com.example.arcanittest.domain.model.Repo

interface ReposRepository {
    fun getRepos(): List<Repo>
}