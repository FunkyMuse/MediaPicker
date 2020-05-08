package com.crazylegend.imagepicker.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        Glide.with(binding.image)
            .load(imageModel.contentUri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(binding.image)
        if (selected){
            binding.selection.setImageResource(R.drawable.ic_checked)
        } else {
            binding.selection.setImageResource(R.drawable.ic_unchecked)
        }
    }
}