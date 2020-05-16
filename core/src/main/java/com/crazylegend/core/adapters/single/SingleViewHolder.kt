package com.crazylegend.core.adapters.single

import com.crazylegend.core.adapters.BaseViewHolder
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.gone
import com.crazylegend.core.modifiers.single.ImageModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

class SingleViewHolder(private val binding: ItemviewImageBinding,
                       private val viewHolderPlaceholderModifier: ImageModifier?, onClick: (BaseCursorModel) -> Unit) :
        BaseViewHolder(binding) {

    fun bind(item: BaseCursorModel) {
        loadImage(binding.image, item.contentUri, viewHolderPlaceholderModifier)
    }


    private val getModel get() = itemView.tag as? BaseCursorModel?

    init {
        itemView.setOnClickListener {
            val model = getModel ?: return@setOnClickListener
            onClick(model)
        }
        binding.selection.gone()
    }


}