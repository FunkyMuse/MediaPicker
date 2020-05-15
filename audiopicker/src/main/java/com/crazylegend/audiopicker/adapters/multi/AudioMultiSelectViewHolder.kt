package com.crazylegend.audiopicker.adapters.multi

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.R
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.core.modifiers.single.ImageButtonModifier
import com.crazylegend.extensions.visible


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class AudioMultiSelectViewHolder(
        private val binding: ItemviewAudioBinding,
        private val modifier: MultiPickerModifier?,
        private val viewHolderPlaceholderModifier: ImageButtonModifier?,
        private val viewHolderTitleTextModifier: TitleTextModifier?

) : RecyclerView.ViewHolder(binding.root) {

    private val selectIconModifier get() = modifier?.selectIconModifier
    private val unSelectedIconModifier get() = modifier?.unSelectedIconModifier

    init {
        binding.selection.visible()
        modifier?.applyGravityWithBottomConstraint(binding.selection, binding.bottomText)
    }

    fun bind(item: AudioModel) {
        binding.bottomText.text = item.displayName
        if (item.thumbnail == null) {
            loadPlaceHolders()
        } else {
            binding.image.setImageBitmap(item.thumbnail)
        }

        viewHolderTitleTextModifier?.apply {
            applyTextParamsConstraint(binding.bottomText)
        }

        if (item.isSelected) {
            setupSelectedImage(binding.selection)
        } else {
            setupUnselectedImage(binding.selection)
        }
    }

    private fun loadPlaceHolders() {
        if (viewHolderPlaceholderModifier != null) {
            viewHolderPlaceholderModifier.resID = viewHolderPlaceholderModifier.resID
                    ?: com.crazylegend.audiopicker.R.drawable.ic_album
            viewHolderPlaceholderModifier.applyImageParamsConstraintLayout(binding.image)
        } else {
            binding.image.setImageResource(com.crazylegend.audiopicker.R.drawable.ic_album)
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