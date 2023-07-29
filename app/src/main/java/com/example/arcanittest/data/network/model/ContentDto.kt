package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class ContentDto(
    @SerializedName("name") val name: String,
    @SerializedName("path") val path: String,
    @SerializedName("type") val type: ContentTypeData,
)