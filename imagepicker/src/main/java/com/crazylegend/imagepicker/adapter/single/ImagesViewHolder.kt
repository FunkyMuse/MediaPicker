package com.crazylegend.imagepicker.adapter.single

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crazylegend.imagepicker.databinding.ItemviewImageBinding
import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesViewHolder(
        private val binding: ItemviewImageBinding,
        onClick: (ImageModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val getModel get() = itemView.tag as? ImageModel?

    init {
        itemView.setOnClickListener {
            val model = getModel ?: return@setOnClickListener
            onClick(model)
        }
    }

    fun bind(imageModel: ImageModel) {
        Glide.with(binding.image)
                .load(imageModel.contentUri)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.image)
    }

}