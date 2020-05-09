package com.crazylegend.videopicker.adapters

import androidx.recyclerview.widget.DiffUtil
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */
internal class VideosDiffUtil : DiffUtil.ItemCallback<VideoModel>() {
    override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel) =
        oldItem == newItem
}