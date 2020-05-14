package com.crazylegend.audiopicker.adapters.single

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.audiopicker.R
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.single.ImageButtonModifier
import com.crazylegend.extensions.gone


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

internal class AudioSingleViewHolder(private val binding: ItemviewAudioBinding, onClick: (AudioModel) -> Unit,
                                     private val viewHolderPlaceholderModifier: ImageButtonModifier?,
                                     private val viewHolderTitleTextModifier: TitleTextModifier?) :
        RecyclerView.ViewHolder(binding.root) {


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
    }

    private fun loadPlaceHolders() {
        if (viewHolderPlaceholderModifier != null) {
            viewHolderPlaceholderModifier.resID = viewHolderPlaceholderModifier.resID
                    ?: R.drawable.ic_album
            viewHolderPlaceholderModifier.applyImageParamsConstraintLayout(binding.image)
        } else {
            binding.image.setImageResource(R.drawable.ic_album)
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