package com.example.youtubeaudio.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val thumbnailUrl: String,
    val durationIso: String,
    val addedAt: Long = System.currentTimeMillis()
)
