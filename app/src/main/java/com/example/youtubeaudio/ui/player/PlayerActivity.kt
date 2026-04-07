package com.example.youtubeaudio.ui.player

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtubeaudio.databinding.ActivityPlayerBinding
import com.example.youtubeaudio.service.AudioPlaybackService
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var youtubePlayer: YouTubePlayer? = null
    private var isPlaying = false
    private var videoId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoId = intent.getStringExtra(EXTRA_VIDEO_ID).orEmpty()
        val title = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        binding.playerTitle.text = title

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                youtubePlayer = player
                player.loadVideo(videoId, 0f)
                startPlaybackService(title)
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                isPlaying = state == PlayerConstants.PlayerState.PLAYING
                binding.playPauseBtn.text = if (isPlaying) "Pause" else "Play"
            }
        })

        binding.playPauseBtn.setOnClickListener {
            val player = youtubePlayer ?: return@setOnClickListener
            if (isPlaying) player.pause() else player.play()
        }

        // Demo controls: next/prev seek around current video.
        binding.nextBtn.setOnClickListener { youtubePlayer?.seekTo(30f) }
        binding.prevBtn.setOnClickListener { youtubePlayer?.seekTo(0f) }
    }

    private fun startPlaybackService(title: String) {
        val intent = Intent(this, AudioPlaybackService::class.java).apply {
            action = AudioPlaybackService.ACTION_START
            putExtra(AudioPlaybackService.EXTRA_TITLE, title)
            putExtra(AudioPlaybackService.EXTRA_VIDEO_ID, videoId)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(intent)
        else startService(intent)
    }

    companion object {
        const val EXTRA_VIDEO_ID = "extra_video_id"
        const val EXTRA_TITLE = "extra_title"
    }
}
