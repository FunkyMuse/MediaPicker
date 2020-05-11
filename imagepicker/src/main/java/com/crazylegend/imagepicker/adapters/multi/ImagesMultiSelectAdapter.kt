package com.crazylegend.imagepicker.adapters.multi

import android.util.SparseBooleanArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.inflater
import com.crazylegend.imagepicker.adapters.ImagesDiffUtil
import com.crazylegend.imagepicker.databinding.ItemviewImageBinding
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.modifiers.multi.MultiImagePickerModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesMultiSelectAdapter(private val modifier: MultiImagePickerModifier?) :
        ListAdapter<ImageModel, ImagesMultiSelectViewHolder>(ImagesDiffUtil()) {

    var selectedPositions = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesMultiSelectViewHolder {
        val holder = ImagesMultiSelectViewHolder(ItemviewImageBinding.inflate(parent.inflater, parent, false),
        modifier)
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


    override fun onBindViewHolder(holder: ImagesMultiSelectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedPositions[position, false])
        holder.itemView.tag = item
    }

}