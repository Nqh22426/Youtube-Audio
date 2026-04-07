package com.example.youtubeaudio.data.api

import com.squareup.moshi.Json

data class YoutubeResponse(
    @Json(name = "items") val items: List<YoutubeVideoItem>
)

data class YoutubeVideoItem(
    @Json(name = "id") val id: String,
    @Json(name = "snippet") val snippet: Snippet,
    @Json(name = "contentDetails") val contentDetails: ContentDetails
)

data class Snippet(
    @Json(name = "title") val title: String,
    @Json(name = "thumbnails") val thumbnails: Thumbnails
)

data class Thumbnails(
    @Json(name = "medium") val medium: Thumbnail?
)

data class Thumbnail(
    @Json(name = "url") val url: String
)

data class ContentDetails(
    @Json(name = "duration") val duration: String
)
