package com.crazylegend.audiopicker.adapters.multi

import android.util.SparseBooleanArray
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

    var selectedPositions = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioMultiSelectViewHolder {
        val holder = AudioMultiSelectViewHolder(ItemviewAudioBinding.inflate(parent.inflater, parent, false), modifier
                , viewHolderPlaceholderModifier, viewHolderTitleTextModifier)
        holder.itemView.setOnClickListener {
            putPosition(holder.adapterPosition)
            notifyItemChanged(holder.adapterPosition)
        }
        return holder
    }

    private fun putPosition(adapterPosition: Int) {
        if (selectedPositions[adapterPosition, false]) {
            selectedPositions.delete(adapterPosition)
        } else {
            selectedPositions.put(adapterPosition, true)
        }
    }

    override fun onBindViewHolder(holderAudio: AudioMultiSelectViewHolder, position: Int) {
        val item = getItem(position)
        holderAudio.bind(item, selectedPositions[position, false])
        holderAudio.itemView.tag = item
    }

}