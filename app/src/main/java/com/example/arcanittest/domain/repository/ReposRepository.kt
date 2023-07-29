package com.example.arcanittest.domain.repository

import com.example.arcanittest.domain.model.Content
import com.example.arcanittest.domain.model.Repo

interface ReposRepository {
    suspend fun searchRepos(query: String): List<Repo>
    suspend fun getContent(repoId: Long, path: String?): List<Content>
}