package com.example.arcanittest.data.network.model

import com.google.gson.annotations.SerializedName

data class FileDto(
    @SerializedName("download_url") val url: String,
)