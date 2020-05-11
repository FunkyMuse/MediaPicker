package com.crazylegend.videopicker.adapters.multi

import android.util.SparseBooleanArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.inflater
import com.crazylegend.videopicker.adapters.VideosDiffUtil
import com.crazylegend.videopicker.databinding.ItemviewVideoBinding
import com.crazylegend.videopicker.modifiers.multi.MultiVideoPickerModifier
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class VideosMultiSelectAdapter(
    private val modifier: MultiVideoPickerModifier?) :
        ListAdapter<VideoModel, VideosMultiSelectViewHolder>(VideosDiffUtil()) {

    var selectedPositions = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosMultiSelectViewHolder {
        val holder = VideosMultiSelectViewHolder(ItemviewVideoBinding.inflate(parent.inflater, parent, false), modifier)
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


    override fun onBindViewHolder(holder: VideosMultiSelectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedPositions[position, false])
        holder.itemView.tag = item
    }

}