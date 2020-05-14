package com.crazylegend.audiopicker.adapters.multi

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.R
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.extensions.visible
import kotlinx.coroutines.CoroutineScope


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class AudioMultiSelectViewHolder(
        private val binding: ItemviewAudioBinding,
        private val modifier: MultiPickerModifier?,
        private val coroutineScope: CoroutineScope

        ) : RecyclerView.ViewHolder(binding.root) {
    private val contentResolver get() = itemView.context.contentResolver

    private val selectIconModifier get() =  modifier?.selectIconModifier
    private val unSelectedIconModifier get() = modifier?.unSelectedIconModifier
    init {
        binding.selection.visible()
        modifier?.applyGravity(binding.selection)
    }

    fun bind(item: AudioModel, selected: Boolean = false) {
        binding.bottomText.text = item.displayName
        item.loadThumbnailScoped(contentResolver, coroutineScope) { thumbnail ->
            if (thumbnail == null) {

            } else {
                binding.image.setImageBitmap(thumbnail)
            }
        }
        if (selected) {
            setupSelectedImage(binding.selection)
        } else {
            setupUnselectedImage(binding.selection)
        }
    }

    private fun setupUnselectedImage(selection: AppCompatImageView) {
        val resID = unSelectedIconModifier?.resID ?: R.drawable.ic_unchecked_default
        unSelectedIconModifier?.applyImageParams(selection,resID)
    }

    private fun setupSelectedImage(selection: AppCompatImageView) {
        val resID = selectIconModifier?.resID ?:R.drawable.ic_checked_default
        selectIconModifier?.applyImageParams(selection,resID)
    }


}