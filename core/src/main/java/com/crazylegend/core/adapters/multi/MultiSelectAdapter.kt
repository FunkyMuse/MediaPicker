package com.crazylegend.core.adapters.multi

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crazylegend.core.adapters.single.SingleDiffUtil
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.inflater
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
class MultiSelectAdapter(private val modifier: BaseMultiPickerModifier?) :
        ListAdapter<BaseCursorModel, MultiSelectViewHolder>(SingleDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val holder = MultiSelectViewHolder(ItemviewImageBinding.inflate(parent.inflater, parent, false), modifier)
        holder.itemView.setOnClickListener {
            val item = getItem(holder.bindingAdapterPosition)
            item.isSelected = !item.isSelected
            notifyItemChanged(holder.bindingAdapterPosition)
        }
        return holder
    }


    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.tag = item
    }

}