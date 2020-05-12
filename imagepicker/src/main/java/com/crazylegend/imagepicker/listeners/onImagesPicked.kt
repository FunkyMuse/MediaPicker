package com.crazylegend.imagepicker.listeners

import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface onImagesPicked {
    fun onImagesPicked(images: List<ImageModel> = emptyList())
}

internal inline fun onImagesDSL(crossinline callback: (list: List<ImageModel>) -> Unit) = object : onImagesPicked {
    override fun onImagesPicked(images: List<ImageModel>) {
        callback(images)
    }
}