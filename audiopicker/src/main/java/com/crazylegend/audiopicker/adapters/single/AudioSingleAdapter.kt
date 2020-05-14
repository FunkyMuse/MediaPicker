package com.crazylegend.audiopicker.adapters.single

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.audiopicker.adapters.AudioDiffUtil
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.databinding.ItemviewAudioBinding
import com.crazylegend.core.inflater
import kotlinx.coroutines.CoroutineScope


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

internal class AudioSingleAdapter (private val coroutineScope: CoroutineScope,private val onClick: (AudioModel) -> Unit) : ListAdapter<AudioModel, AudioSingleViewHolder>(AudioDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
            AudioSingleViewHolder(ItemviewAudioBinding.inflate(parent.inflater, parent, false), onClick, coroutineScope)

    override fun onBindViewHolder(holderAudio: AudioSingleViewHolder, position: Int) {
        val item = getItem(position)
        holderAudio.bind(item)
        holderAudio.itemView.tag = item
    }

}