package com.example.youtubeaudio.ui.playlist

import androidx.lifecycle.ViewModel
import com.example.youtubeaudio.data.repository.PlaylistRepository

class PlaylistViewModel(repository: PlaylistRepository) : ViewModel() {
    val items = repository.observeAll()
}
