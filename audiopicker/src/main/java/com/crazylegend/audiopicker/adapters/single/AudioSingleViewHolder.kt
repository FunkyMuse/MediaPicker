package com.crazylegend.audiopicker.adapters.single

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.audiopicker.R
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.extensions.gone
import kotlinx.coroutines.CoroutineScope


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

internal class AudioSingleViewHolder(private val binding: ItemviewAudioBinding, onClick: (AudioModel) -> Unit, private val coroutineScope: CoroutineScope) :
        RecyclerView.ViewHolder(binding.root) {

    private val contentResolver get() = itemView.context.contentResolver

    fun bind(item: AudioModel) {
        binding.bottomText.text = item.displayName
        item.loadThumbnailScoped(contentResolver, coroutineScope) { thumbnail ->
            if (thumbnail == null) {
                binding.image.setImageResource(R.drawable.ic_album)
            } else {
                binding.image.setImageBitmap(thumbnail)
            }
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