package com.crazylegend.audiopicker.adapters.multi

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.audiopicker.adapters.AudioDiffUtil
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.inflater
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.core.modifiers.single.ImageButtonModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class AudioMultiSelectAdapter(private val modifier: MultiPickerModifier?,
                                       private val viewHolderPlaceholderModifier: ImageButtonModifier?,
                                       private val viewHolderTitleTextModifier: TitleTextModifier?) :
        ListAdapter<AudioModel, AudioMultiSelectViewHolder>(AudioDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioMultiSelectViewHolder {
        val holder = AudioMultiSelectViewHolder(ItemviewAudioBinding.inflate(parent.inflater, parent, false), modifier
                , viewHolderPlaceholderModifier, viewHolderTitleTextModifier)
        holder.itemView.setOnClickListener {
            val item = getItem(holder.adapterPosition)
            item.isSelected = !item.isSelected
            notifyItemChanged(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holderAudio: AudioMultiSelectViewHolder, position: Int) {
        val item = getItem(position)
        holderAudio.bind(item)
        holderAudio.itemView.tag = item
    }

}