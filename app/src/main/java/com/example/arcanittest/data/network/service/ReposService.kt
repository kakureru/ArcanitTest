package com.example.arcanittest.data.network.service

import com.example.arcanittest.data.network.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReposService {
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse
}