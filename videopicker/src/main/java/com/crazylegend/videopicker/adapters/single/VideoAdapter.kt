package com.crazylegend.videopicker.adapters.single

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.inflater
import com.crazylegend.videopicker.adapters.VideosDiffUtil
import com.crazylegend.videopicker.databinding.ItemviewVideoBinding
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class VideoAdapter(private val onClick: (VideoModel) -> Unit) : ListAdapter<VideoModel, VideoViewHolder>(VideosDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            VideoViewHolder(ItemviewVideoBinding.inflate(parent.inflater, parent, false), onClick)

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.tag = item
    }


}