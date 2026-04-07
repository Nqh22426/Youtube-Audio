# YouTube Audio (Android)

A lightweight Kotlin Android app for saving and playing YouTube video audio sessions with a clean UI.

> Compliance note: the app does **not** download YouTube audio/video. It uses YouTube metadata (Data API v3) and an embedded YouTube player.

## Tech stack
- Kotlin + Android Studio (Gradle Kotlin DSL)
- MVVM architecture (ViewModel + Repository)
- Retrofit + Moshi for YouTube Data API v3
- Room for local playlist (favorites)
- Foreground Service + media-style notification for background-friendly behavior
- Material Design 3 UI

## Project structure
- `app/src/main/java/com/example/youtubeaudio/data` → API + Room + repositories
- `app/src/main/java/com/example/youtubeaudio/ui` → input, player, playlist screens
- `app/src/main/java/com/example/youtubeaudio/service` → foreground playback service
- `app/src/main/java/com/example/youtubeaudio/util` → URL parser

## Setup (beginner-friendly)
1. Install Android Studio Hedgehog or newer.
2. Open this folder as a project.
3. Get a YouTube Data API v3 key:
   - Go to Google Cloud Console.
   - Create/select a project.
   - Enable **YouTube Data API v3**.
   - Create an API key.
4. Add your API key in `~/.gradle/gradle.properties` (recommended):
   ```properties
   YOUTUBE_API_KEY=YOUR_KEY_HERE
   ```
   Or project-local `gradle.properties`.
5. Sync Gradle.
6. Run on an Android device/emulator (Android 7.0+).

## How to use
1. Open app → **Input** tab.
2. Paste a YouTube URL (e.g. `https://www.youtube.com/watch?v=dQw4w9WgXcQ`).
3. Tap **Load Video** (metadata fetched from YouTube API).
4. Tap **Open Player** to start playback.
5. Tap **Save to playlist** to keep it locally in favorites.
6. Open **Playlist** tab to view saved entries.

## Simple test cases
1. **URL parsing + metadata**
   - Paste valid URL and tap *Load Video*.
   - Expected: title + duration appear.
2. **Playback**
   - Tap *Open Player*.
   - Expected: YouTube player loads and plays.
3. **Background-friendly service**
   - Start playback, leave app to home.
   - Expected: foreground notification remains visible with media actions.
4. **Playlist persistence**
   - Save a video, force close app, reopen.
   - Expected: saved item still in playlist.

## Google Play / policy alignment
- No media file downloading.
- No local YouTube media caching.
- Playback uses embedded player behavior.
- Metadata fetched through YouTube Data API v3.

## Performance notes
- Lean dependency set and basic XML UI for low overhead.
- Room used only for small metadata records (favorites).
- Network calls done on background coroutines.
