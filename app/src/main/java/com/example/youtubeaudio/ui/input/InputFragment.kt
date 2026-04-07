package com.example.youtubeaudio.ui.input

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.youtubeaudio.YoutubeAudioApp
import com.example.youtubeaudio.databinding.FragmentInputBinding
import com.example.youtubeaudio.ui.common.AppViewModelFactory
import com.example.youtubeaudio.ui.player.PlayerActivity

class InputFragment : Fragment() {
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InputViewModel by activityViewModels {
        val container = (requireActivity().application as YoutubeAudioApp).container
        AppViewModelFactory(container.youtubeRepository, container.playlistRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loadButton.setOnClickListener {
            viewModel.loadVideo(binding.urlInput.text?.toString().orEmpty())
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveCurrentVideo()
        }

        binding.openPlayerButton.setOnClickListener {
            val video = viewModel.currentVideo.value ?: return@setOnClickListener
            startActivity(Intent(requireContext(), PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.EXTRA_VIDEO_ID, video.videoId)
                putExtra(PlayerActivity.EXTRA_TITLE, video.title)
            })
        }

        viewModel.currentVideo.observe(viewLifecycleOwner) { video ->
            binding.videoTitle.text = video?.title.orEmpty()
            binding.videoDuration.text = video?.durationIso.orEmpty()
            val enabled = video != null
            binding.openPlayerButton.isEnabled = enabled
            binding.saveButton.isEnabled = enabled
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
