package com.crazylegend.core.adapters.single

import androidx.core.view.isVisible
import com.crazylegend.core.adapters.BaseViewHolder
import com.crazylegend.core.bytesToFormattedString
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.gone
import com.crazylegend.core.modifiers.SizeTextModifier
import com.crazylegend.core.modifiers.single.ImageModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

class SingleViewHolder(private val binding: ItemviewImageBinding,
                       private val viewHolderPlaceholderModifier: ImageModifier?, private val sizeTextModifier: SizeTextModifier?, onClick: (BaseCursorModel) -> Unit) :
        BaseViewHolder(binding) {

    fun bind(item: BaseCursorModel, showFileSize: Boolean) {
        loadImage(binding.image, item.contentUri, viewHolderPlaceholderModifier)
        if (showFileSize) {
            sizeTextModifier?.applyTextParams(binding.size)
            sizeTextModifier?.applyTextParamsConstraint(binding.size)
            binding.size.isVisible = false
            item.size?.let { size ->
                binding.size.isVisible = size > 0
                binding.size.text = size.bytesToFormattedString()
            }
        }
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