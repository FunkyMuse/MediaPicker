package com.crazylegend.videopicker.adapters.multi

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.loadImage
import com.crazylegend.core.visible
import com.crazylegend.videopicker.R
import com.crazylegend.videopicker.databinding.ItemviewVideoBinding
import com.crazylegend.videopicker.modifiers.multi.MultiVideoPickerModifier
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class VideosMultiSelectViewHolder(
    private val binding: ItemviewVideoBinding,
    private val modifier: MultiVideoPickerModifier?
) : RecyclerView.ViewHolder(binding.root) {


    private val selectIconModifier get() =  modifier?.selectIconModifier
    private val unSelectedIconModifier get() = modifier?.unSelectedIconModifier
    init {
        binding.selection.visible()
        modifier?.applyGravity(binding.selection)
    }

    fun bind(imageModel: VideoModel, selected: Boolean = false) {
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