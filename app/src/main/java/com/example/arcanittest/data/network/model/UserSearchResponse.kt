package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<UserDto> = emptyList(),
)