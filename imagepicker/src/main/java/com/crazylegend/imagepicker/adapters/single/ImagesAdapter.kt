package com.crazylegend.imagepicker.adapters.single

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.inflater
import com.crazylegend.imagepicker.adapters.ImagesDiffUtil
import com.crazylegend.imagepicker.databinding.ItemviewImageBinding
import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesAdapter(private val onClick: (ImageModel) -> Unit) :
        ListAdapter<ImageModel, ImagesViewHolder>(
                ImagesDiffUtil()
        ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ImagesViewHolder(
                    ItemviewImageBinding.inflate(
                            parent.inflater,
                            parent,
                            false
                    ), onClick
            )


    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.tag = item
    }


}