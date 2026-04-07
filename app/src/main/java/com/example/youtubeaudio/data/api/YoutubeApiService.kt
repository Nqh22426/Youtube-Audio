package com.example.youtubeaudio.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {
    @GET("youtube/v3/videos")
    suspend fun getVideo(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("id") id: String,
        @Query("key") key: String
    ): YoutubeResponse
}
