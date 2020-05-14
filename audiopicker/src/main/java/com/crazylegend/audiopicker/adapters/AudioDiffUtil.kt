package com.crazylegend.audiopicker.adapters

import androidx.recyclerview.widget.DiffUtil
import com.crazylegend.audiopicker.audios.AudioModel


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */
internal class AudioDiffUtil : DiffUtil.ItemCallback<AudioModel>() {
    override fun areItemsTheSame(oldItem: AudioModel, newItem: AudioModel) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AudioModel, newItem: AudioModel) =
            oldItem == newItem
}