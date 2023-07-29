package com.example.arcanittest.data.network.service

import com.example.arcanittest.data.network.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): UserSearchResponse
}