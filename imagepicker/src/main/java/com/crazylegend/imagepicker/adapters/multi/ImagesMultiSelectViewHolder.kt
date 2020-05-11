package com.crazylegend.imagepicker.adapters.multi

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.loadImage
import com.crazylegend.core.visible
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.databinding.ItemviewImageBinding
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.modifiers.multi.MultiImagePickerModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesMultiSelectViewHolder(
    private val binding: ItemviewImageBinding,
    private val multiImagePickerModifier: MultiImagePickerModifier?

) : RecyclerView.ViewHolder(binding.root) {

    private val selectIconModifier get() =  multiImagePickerModifier?.selectIconModifier
    private val unSelectedIconModifier get() = multiImagePickerModifier?.unSelectedIconModifier
    init {
        binding.selection.visible()
        multiImagePickerModifier?.applyGravity(binding.selection)
    }

    fun bind(imageModel: ImageModel, selected: Boolean = false) {
        binding.image.loadImage(imageModel.contentUri)
        if (selected) {
            setupSelectedImage(binding.selection)
        } else {
            setupUnselectedImage(binding.selection)
        }
    }

    private fun setupUnselectedImage(selection: AppCompatImageView) {
        val resID = unSelectedIconModifier?.resID ?:R.drawable.ic_unchecked_default
        unSelectedIconModifier?.applyImageParams(selection,resID)
    }

    private fun setupSelectedImage(selection: AppCompatImageView) {
        val resID = selectIconModifier?.resID ?:R.drawable.ic_checked_default
        selectIconModifier?.applyImageParams(selection,resID)
    }
}