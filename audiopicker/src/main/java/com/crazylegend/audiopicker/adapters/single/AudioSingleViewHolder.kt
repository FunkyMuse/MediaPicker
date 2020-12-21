package com.crazylegend.audiopicker.adapters.single

import com.bumptech.glide.Glide
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.adapters.BaseViewHolder
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.single.ImageModifier
import com.crazylegend.extensions.gone


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

internal class AudioSingleViewHolder(private val binding: ItemviewAudioBinding, onClick: (AudioModel) -> Unit,
                                     private val viewHolderPlaceholderModifier: ImageModifier?,
                                     private val viewHolderTitleTextModifier: TitleTextModifier?) :
        BaseViewHolder(binding) {


    fun bind(item: AudioModel) {
        binding.bottomText.text = item.displayName
        if (item.thumbnail == null) {
            loadPlaceHolders(viewHolderPlaceholderModifier, binding.image)
        } else {
            Glide
                    .with(binding.image)
                    .load(item.thumbnail)
                    .into(binding.image)
        }
        viewHolderTitleTextModifier?.apply {
            applyTextParamsConstraint(binding.bottomText)
        }
    }

    private val getModel get() = itemView.tag as? AudioModel?

    init {
        itemView.setOnClickListener {
            val model = getModel ?: return@setOnClickListener
            onClick(model)
        }
        binding.selection.gone()
    }


}