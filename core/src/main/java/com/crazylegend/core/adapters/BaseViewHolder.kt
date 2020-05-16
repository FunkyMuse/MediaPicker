package com.crazylegend.core.adapters

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.crazylegend.core.R
import com.crazylegend.core.loadImage
import com.crazylegend.core.modifiers.multi.SelectIconModifier
import com.crazylegend.core.modifiers.single.ImageModifier


/**
 * Created by crazy on 5/16/20 to long live and prosper !
 */
open class BaseViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun loadImage(image: AppCompatImageView, contentUri: Uri, viewHolderPlaceholderModifier: ImageModifier?) {
        image.loadImage(contentUri) {
            if (viewHolderPlaceholderModifier != null) {
                loadPlaceHolders(viewHolderPlaceholderModifier, image)
            }
        }
    }

    fun loadPlaceHolders(viewHolderPlaceholderModifier: ImageModifier?, image: AppCompatImageView) {
        if (viewHolderPlaceholderModifier != null) {
            viewHolderPlaceholderModifier.resID = viewHolderPlaceholderModifier.resID
                    ?: R.drawable.ic_image
            viewHolderPlaceholderModifier.applyImageParamsConstraintLayout(image)
        } else {
            image.setImageResource(R.drawable.ic_image)
        }
    }

    fun setupUnselectedImage(selection: AppCompatImageView, unSelectedIconModifier: SelectIconModifier?) {
        val resID = unSelectedIconModifier?.resID ?: R.drawable.ic_unchecked_default
        unSelectedIconModifier?.applyImageParams(selection, resID)
    }

    fun setupSelectedImage(selection: AppCompatImageView, selectIconModifier: SelectIconModifier?) {
        val resID = selectIconModifier?.resID ?: R.drawable.ic_checked_default
        selectIconModifier?.applyImageParams(selection, resID)
    }
}