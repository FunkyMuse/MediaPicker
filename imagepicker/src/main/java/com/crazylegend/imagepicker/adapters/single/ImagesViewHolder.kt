package com.crazylegend.imagepicker.adapters.single

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.loadImage
import com.crazylegend.imagepicker.databinding.ItemviewImageBinding
import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesViewHolder(private val binding: ItemviewImageBinding, onClick: (ImageModel) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val getModel get() = itemView.tag as? ImageModel?

    init {
        itemView.setOnClickListener {
            val model = getModel ?: return@setOnClickListener
            onClick(model)
        }
    }

    fun bind(imageModel: ImageModel) {
        binding.image.loadImage(imageModel.contentUri)
    }

}