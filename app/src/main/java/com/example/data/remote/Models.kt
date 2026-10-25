package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FetchRequest(
    val url: String
)

@JsonClass(generateAdapter = true)
data class FetchResponse(
    val type: String, // video, photo, reel, story, igtv, carousel, profile
    val thumbnail: String,
    val caption: String?,
    val owner: String?,
    val items: List<DownloadItem>
)

@JsonClass(generateAdapter = true)
data class DownloadItem(
    val type: String,
    val previewUrl: String,
    val downloadUrl: String,
    val qualityLabel: String?,
    val sizeLabel: String?
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val error: String
)
