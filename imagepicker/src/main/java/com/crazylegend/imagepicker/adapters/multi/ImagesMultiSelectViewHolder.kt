package com.crazylegend.imagepicker.adapters.multi

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.visible
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.databinding.ItemviewImageBinding
import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesMultiSelectViewHolder(
        private val binding: ItemviewImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.selection.visible()
    }

    fun bind(imageModel: ImageModel, selected: Boolean = false) {
        binding.image.loadImage(imageModel.contentUri)

        if (selected) {
            binding.selection.setImageResource(R.drawable.ic_checked)
        } else {
            binding.selection.setImageResource(R.drawable.ic_unchecked)
        }
    }
}