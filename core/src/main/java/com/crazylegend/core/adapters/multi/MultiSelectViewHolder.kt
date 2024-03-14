package com.crazylegend.core.adapters.multi

import androidx.core.view.isVisible
import com.crazylegend.core.adapters.BaseViewHolder
import com.crazylegend.core.bytesToFormattedString
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.core.visible


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
class MultiSelectViewHolder(
        private val binding: ItemviewImageBinding,
        private val modifier: BaseMultiPickerModifier?
) : BaseViewHolder(binding) {

    private val selectIconModifier get() = modifier?.selectIconModifier
    private val unSelectedIconModifier get() = modifier?.unSelectedIconModifier
    private val viewHolderPlaceholderModifier get() = modifier?.viewHolderPlaceholderModifier

    init {
        binding.selection.visible()
        modifier?.applyGravity(binding.selection)
    }

    fun bind(cursorModel: BaseCursorModel, showFileSize: Boolean) {
        loadImage(binding.image, cursorModel.contentUri, viewHolderPlaceholderModifier)
        if (showFileSize) {
            modifier?.sizeTextModifier?.applyTextParams(binding.size)
            modifier?.sizeTextModifier?.applyTextParamsConstraint(binding.size)
            binding.size.isVisible = false
            cursorModel.size?.let { size ->
                binding.size.isVisible = size > 0
                binding.size.text = size.bytesToFormattedString()
            }
        }
        if (cursorModel.isSelected) {
            setupSelectedImage(binding.selection, selectIconModifier)
        } else {
            setupUnselectedImage(binding.selection, unSelectedIconModifier)
        }
    }


}
