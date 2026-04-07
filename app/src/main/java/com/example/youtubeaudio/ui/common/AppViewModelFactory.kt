package com.example.youtubeaudio.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeaudio.data.repository.PlaylistRepository
import com.example.youtubeaudio.data.repository.YoutubeRepository
import com.example.youtubeaudio.ui.input.InputViewModel
import com.example.youtubeaudio.ui.playlist.PlaylistViewModel

class AppViewModelFactory(
    private val youtubeRepository: YoutubeRepository,
    private val playlistRepository: PlaylistRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(InputViewModel::class.java) ->
                InputViewModel(youtubeRepository, playlistRepository) as T
            modelClass.isAssignableFrom(PlaylistViewModel::class.java) ->
                PlaylistViewModel(playlistRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
