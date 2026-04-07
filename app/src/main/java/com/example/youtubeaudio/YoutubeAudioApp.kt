package com.example.youtubeaudio

import android.app.Application
import com.example.youtubeaudio.data.repository.AppContainer

class YoutubeAudioApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
