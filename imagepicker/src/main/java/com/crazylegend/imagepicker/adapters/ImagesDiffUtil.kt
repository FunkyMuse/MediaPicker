package com.crazylegend.imagepicker.adapters

import androidx.recyclerview.widget.DiffUtil
import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesDiffUtil : DiffUtil.ItemCallback<ImageModel>() {
    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel) =
            oldItem == newItem
}