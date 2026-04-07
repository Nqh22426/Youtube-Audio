package com.example.youtubeaudio.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.media.session.MediaButtonReceiver
import com.example.youtubeaudio.MainActivity
import com.example.youtubeaudio.R

/**
 * Foreground service for background-friendly playback state + media notification controls.
 * Actual YouTube playback is handled by the embedded YouTube player in PlayerActivity
 * to stay aligned with YouTube ToS (no media extraction / local downloads).
 */
class AudioPlaybackService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                createChannelIfNeeded()
                startForeground(NOTIFICATION_ID, buildNotification(intent))
            }
            ACTION_STOP -> {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
            Intent.ACTION_MEDIA_BUTTON -> MediaButtonReceiver.handleIntent(null, intent)
        }
        return START_STICKY
    }

    private fun buildNotification(intent: Intent): Notification {
        val title = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle(title)
            .setContentText("YouTube audio session")
            .setContentIntent(contentIntent)
            .setOngoing(true)
            .addAction(
                android.R.drawable.ic_media_pause,
                getString(R.string.pause),
                mediaButtonIntent(android.view.KeyEvent.KEYCODE_MEDIA_PAUSE)
            )
            .addAction(
                android.R.drawable.ic_media_next,
                getString(R.string.next),
                mediaButtonIntent(android.view.KeyEvent.KEYCODE_MEDIA_NEXT)
            )
            .addAction(
                android.R.drawable.ic_media_previous,
                getString(R.string.previous),
                mediaButtonIntent(android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS)
            )
            .build()
    }

    private fun mediaButtonIntent(keyCode: Int): PendingIntent {
        val mediaButtonIntent = Intent(Intent.ACTION_MEDIA_BUTTON)
        mediaButtonIntent.setClass(this, MediaButtonReceiver::class.java)
        mediaButtonIntent.putExtra(
            Intent.EXTRA_KEY_EVENT,
            android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, keyCode)
        )
        return PendingIntent.getBroadcast(
            this,
            keyCode,
            mediaButtonIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Playback",
            NotificationManager.IMPORTANCE_LOW
        )
        manager.createNotificationChannel(channel)
    }

    companion object {
        const val ACTION_START = "com.example.youtubeaudio.action.START"
        const val ACTION_STOP = "com.example.youtubeaudio.action.STOP"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_VIDEO_ID = "extra_video_id"
        private const val CHANNEL_ID = "playback_channel"
        private const val NOTIFICATION_ID = 7
    }
}
