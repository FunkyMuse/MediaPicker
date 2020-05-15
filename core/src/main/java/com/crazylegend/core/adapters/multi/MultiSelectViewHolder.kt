package com.crazylegend.core.adapters.multi

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.R
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.loadImage
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.core.visible


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
class MultiSelectViewHolder(
        private val binding: ItemviewImageBinding,
        private val modifier: MultiPickerModifier?

) : RecyclerView.ViewHolder(binding.root) {

    private val selectIconModifier get() = modifier?.selectIconModifier
    private val unSelectedIconModifier get() = modifier?.unSelectedIconModifier

    init {
        binding.selection.visible()
        modifier?.applyGravity(binding.selection)
    }

    fun bind(cursorModel: BaseCursorModel) {
        binding.image.loadImage(cursorModel.contentUri)
        if (cursorModel.isSelected) {
            setupSelectedImage(binding.selection)
        } else {
            setupUnselectedImage(binding.selection)
        }
    }

    private fun setupUnselectedImage(selection: AppCompatImageView) {
        val resID = unSelectedIconModifier?.resID ?: R.drawable.ic_unchecked_default
        unSelectedIconModifier?.applyImageParams(selection, resID)
    }

    private fun setupSelectedImage(selection: AppCompatImageView) {
        val resID = selectIconModifier?.resID ?: R.drawable.ic_checked_default
        selectIconModifier?.applyImageParams(selection, resID)
    }


}