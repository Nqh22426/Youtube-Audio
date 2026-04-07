package com.example.youtubeaudio.ui.input

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeaudio.data.repository.PlaylistRepository
import com.example.youtubeaudio.data.repository.YoutubeRepository
import com.example.youtubeaudio.domain.VideoItem
import com.example.youtubeaudio.util.YoutubeUrlParser
import kotlinx.coroutines.launch

class InputViewModel(
    private val youtubeRepository: YoutubeRepository,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    val currentVideo = MutableLiveData<VideoItem?>()
    val message = MutableLiveData<String>()

    fun loadVideo(urlOrId: String) {
        val id = YoutubeUrlParser.extractVideoId(urlOrId)
        if (id == null) {
            message.value = "Invalid YouTube URL"
            return
        }
        viewModelScope.launch {
            runCatching { youtubeRepository.fetchVideo(id) }
                .onSuccess {
                    currentVideo.postValue(it)
                    if (it == null) message.postValue("Video not found or API key missing")
                }
                .onFailure { message.postValue(it.message ?: "Failed to load video") }
        }
    }

    fun saveCurrentVideo() {
        val video = currentVideo.value ?: return
        viewModelScope.launch {
            playlistRepository.save(video)
            message.postValue("Saved to playlist")
        }
    }
}
