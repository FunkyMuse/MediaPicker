package com.crazylegend.videopicker.adapters.single

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.loadImage
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
        binding.image.loadImage(imageModel.contentUri)

    }

}