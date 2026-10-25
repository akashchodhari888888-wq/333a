package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "download_history")
data class DownloadHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val type: String, // video, photo, reel, story, igtv, carousel, profile
    val thumbnail: String,
    val caption: String?,
    val owner: String?,
    val timestamp: Long = System.currentTimeMillis()
)
