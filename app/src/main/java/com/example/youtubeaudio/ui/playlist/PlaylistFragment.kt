package com.example.youtubeaudio.ui.playlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeaudio.YoutubeAudioApp
import com.example.youtubeaudio.databinding.FragmentPlaylistBinding
import com.example.youtubeaudio.ui.common.AppViewModelFactory
import com.example.youtubeaudio.ui.player.PlayerActivity

class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistViewModel by activityViewModels {
        val container = (requireActivity().application as YoutubeAudioApp).container
        AppViewModelFactory(container.youtubeRepository, container.playlistRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = PlaylistAdapter { video ->
            startActivity(Intent(requireContext(), PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.EXTRA_VIDEO_ID, video.videoId)
                putExtra(PlayerActivity.EXTRA_TITLE, video.title)
            })
        }
        binding.playlistRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.playlistRecycler.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
