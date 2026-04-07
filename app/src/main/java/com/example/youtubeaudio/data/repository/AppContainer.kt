package com.example.youtubeaudio.data.repository

import android.content.Context
import androidx.room.Room
import com.example.youtubeaudio.data.api.YoutubeApiService
import com.example.youtubeaudio.data.db.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainer(context: Context) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "youtube_audio.db"
    ).build()

    val youtubeRepository = YoutubeRepository(retrofit.create(YoutubeApiService::class.java))
    val playlistRepository = PlaylistRepository(db.playlistDao())
}
