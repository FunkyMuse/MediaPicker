package com.crazylegend.videopicker.adapters.single

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crazylegend.videopicker.databinding.ItemviewVideoBinding
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class VideoViewHolder(private val binding: ItemviewVideoBinding, onClick: (VideoModel) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val getModel get() = itemView.tag as? VideoModel?

    init {
        itemView.setOnClickListener {
            val model = getModel ?: return@setOnClickListener
            onClick(model)
        }
    }

    fun bind(imageModel: VideoModel) {
        Glide.with(binding.image)
                .load(imageModel.contentUri)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.image)
    }

}