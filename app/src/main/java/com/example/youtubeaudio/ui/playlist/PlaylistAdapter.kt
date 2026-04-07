package com.example.youtubeaudio.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeaudio.databinding.ItemPlaylistBinding
import com.example.youtubeaudio.domain.VideoItem

class PlaylistAdapter(
    private val onClick: (VideoItem) -> Unit
) : ListAdapter<VideoItem, PlaylistAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<VideoItem>() {
        override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
            oldItem.videoId == newItem.videoId

        override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class VH(
        private val binding: ItemPlaylistBinding,
        private val onClick: (VideoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoItem) {
            binding.title.text = item.title
            binding.duration.text = item.durationIso
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
