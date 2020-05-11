package com.crazylegend.core.adapters.multi

import android.util.SparseBooleanArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.adapters.single.SingleDiffUtil
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.inflater
import com.crazylegend.core.modifiers.multi.MultiPickerModifier



/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
class MultiSelectAdapter(private val modifier: MultiPickerModifier?) :
        ListAdapter<BaseCursorModel, MultiSelectViewHolder>(SingleDiffUtil()) {

    var selectedPositions = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val holder = MultiSelectViewHolder(ItemviewImageBinding.inflate(parent.inflater, parent, false), modifier)
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

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedPositions[position, false])
        holder.itemView.tag = item
    }

}