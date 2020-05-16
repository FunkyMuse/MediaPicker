package com.crazylegend.core.adapters.single

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.inflater
import com.crazylegend.core.modifiers.single.ImageModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

open class SingleAdapter(private val viewHolderPlaceholderModifier: ImageModifier?,
                         private val onClick: (BaseCursorModel) -> Unit) : ListAdapter<BaseCursorModel, SingleViewHolder>(SingleDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewBaseCursorModelype: Int) =
            SingleViewHolder(ItemviewImageBinding.inflate(parent.inflater, parent, false), viewHolderPlaceholderModifier, onClick)

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.tag = item
    }

}