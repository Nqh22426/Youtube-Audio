package com.example.youtubeaudio.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.youtubeaudio.data.db.PlaylistDao
import com.example.youtubeaudio.data.db.PlaylistEntity
import com.example.youtubeaudio.domain.VideoItem

class PlaylistRepository(private val dao: PlaylistDao) {
    fun observeAll(): LiveData<List<VideoItem>> = dao.observeAll().map { list ->
        list.map {
            VideoItem(
                videoId = it.videoId,
                title = it.title,
                thumbnailUrl = it.thumbnailUrl,
                durationIso = it.durationIso
            )
        }
    }

    suspend fun save(video: VideoItem) {
        dao.upsert(
            PlaylistEntity(
                videoId = video.videoId,
                title = video.title,
                thumbnailUrl = video.thumbnailUrl,
                durationIso = video.durationIso
            )
        )
    }
}
