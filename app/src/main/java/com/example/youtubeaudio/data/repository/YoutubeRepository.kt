package com.example.youtubeaudio.data.repository

import com.example.youtubeaudio.BuildConfig
import com.example.youtubeaudio.data.api.YoutubeApiService
import com.example.youtubeaudio.domain.VideoItem

class YoutubeRepository(
    private val api: YoutubeApiService
) {
    suspend fun fetchVideo(videoId: String): VideoItem? {
        if (BuildConfig.YOUTUBE_API_KEY.isBlank()) return null
        val response = api.getVideo(id = videoId, key = BuildConfig.YOUTUBE_API_KEY)
        val item = response.items.firstOrNull() ?: return null
        return VideoItem(
            videoId = item.id,
            title = item.snippet.title,
            thumbnailUrl = item.snippet.thumbnails.medium?.url.orEmpty(),
            durationIso = item.contentDetails.duration
        )
    }
}
