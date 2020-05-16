package com.crazylegend.audiopicker.adapters.multi

import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.adapters.BaseViewHolder
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.core.modifiers.single.ImageModifier
import com.crazylegend.extensions.visible


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class AudioMultiSelectViewHolder(
        private val binding: ItemviewAudioBinding,
        private val modifier: BaseMultiPickerModifier?,
        private val viewHolderPlaceholderModifier: ImageModifier?,
        private val viewHolderTitleTextModifier: TitleTextModifier?

) : BaseViewHolder(binding) {

    private val selectIconModifier get() = modifier?.selectIconModifier
    private val unSelectedIconModifier get() = modifier?.unSelectedIconModifier

    init {
        binding.selection.visible()
        modifier?.applyGravityWithBottomConstraint(binding.selection, binding.bottomText)
    }

    fun bind(item: AudioModel) {
        binding.bottomText.text = item.displayName
        if (item.thumbnail == null) {
            loadPlaceHolders(viewHolderPlaceholderModifier, binding.image)
        } else {
            binding.image.setImageBitmap(item.thumbnail)
        }

        viewHolderTitleTextModifier?.apply {
            applyTextParamsConstraint(binding.bottomText)
        }

        if (item.isSelected) {
            setupSelectedImage(binding.selection, selectIconModifier)
        } else {
            setupUnselectedImage(binding.selection, unSelectedIconModifier)
        }
    }


}