package com.crazylegend.audiopicker.adapters.single

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.audiopicker.adapters.AudioDiffUtil
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.inflater
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.single.ImageModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

internal class AudioSingleAdapter(private val viewHolderPlaceholderModifier: ImageModifier?,
                                  private val viewHolderTitleTextModifier: TitleTextModifier?,
                                  private val onClick: (AudioModel) -> Unit) : ListAdapter<AudioModel, AudioSingleViewHolder>(AudioDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
            AudioSingleViewHolder(ItemviewAudioBinding.inflate(parent.inflater, parent, false), onClick,
                    viewHolderPlaceholderModifier, viewHolderTitleTextModifier)

    override fun onBindViewHolder(holderAudio: AudioSingleViewHolder, position: Int) {
        val item = getItem(position)
        holderAudio.bind(item)
        holderAudio.itemView.tag = item
    }

}