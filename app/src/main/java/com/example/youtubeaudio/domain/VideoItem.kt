package com.example.youtubeaudio.domain

data class VideoItem(
    val videoId: String,
    val title: String,
    val thumbnailUrl: String,
    val durationIso: String
)
