package com.example.youtubeaudio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.youtubeaudio.databinding.ActivityMainBinding
import com.example.youtubeaudio.ui.input.InputFragment
import com.example.youtubeaudio.ui.playlist.PlaylistFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(binding.container.id, InputFragment())
            }
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_input -> supportFragmentManager.commit {
                    replace(binding.container.id, InputFragment())
                }
                R.id.nav_playlist -> supportFragmentManager.commit {
                    replace(binding.container.id, PlaylistFragment())
                }
            }
            true
        }
    }
}
