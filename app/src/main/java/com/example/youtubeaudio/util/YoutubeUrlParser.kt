package com.example.youtubeaudio.util

import android.net.Uri

object YoutubeUrlParser {
    fun extractVideoId(urlOrId: String): String? {
        if (!urlOrId.contains("http")) return urlOrId.takeIf { it.length >= 6 }
        val uri = Uri.parse(urlOrId)
        val host = uri.host ?: return null
        return when {
            host.contains("youtu.be") -> uri.lastPathSegment
            host.contains("youtube.com") -> uri.getQueryParameter("v")
            else -> null
        }
    }
}
