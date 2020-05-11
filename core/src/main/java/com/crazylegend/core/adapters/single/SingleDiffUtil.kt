package com.crazylegend.core.adapters.single

import androidx.recyclerview.widget.DiffUtil
import com.crazylegend.core.dto.BaseCursorModel


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */
class SingleDiffUtil : DiffUtil.ItemCallback<BaseCursorModel>() {
    override fun areItemsTheSame(oldItem: BaseCursorModel, newItem: BaseCursorModel) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BaseCursorModel, newItem: BaseCursorModel) =
            oldItem == newItem
}