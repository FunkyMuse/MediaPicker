package com.crazylegend.core.adapters.single

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.databinding.ItemviewImageBinding
import com.crazylegend.core.dto.BaseCursorModel
import com.crazylegend.core.gone
import com.crazylegend.core.loadImage


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

class SingleViewHolder(private val binding: ItemviewImageBinding, onClick: (BaseCursorModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BaseCursorModel) {
        binding.image.loadImage(item.contentUri)
    }

    private val getModel get() = itemView.tag as? BaseCursorModel?

    init {
        itemView.setOnClickListener {
            val model = getModel ?: return@setOnClickListener
            onClick(model)
        }
        binding.selection.gone()
    }


}