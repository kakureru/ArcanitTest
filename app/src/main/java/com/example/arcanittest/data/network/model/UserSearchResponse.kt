package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("items") val items: List<UserDto> = emptyList(),
)