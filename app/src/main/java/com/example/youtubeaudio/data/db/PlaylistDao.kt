package com.example.youtubeaudio.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: PlaylistEntity)

    @Query("SELECT * FROM playlist ORDER BY addedAt DESC")
    fun observeAll(): LiveData<List<PlaylistEntity>>
}
