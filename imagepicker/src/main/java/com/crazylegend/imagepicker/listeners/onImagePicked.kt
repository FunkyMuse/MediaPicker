package com.crazylegend.imagepicker.listeners

import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface onImagePicked {
    fun forImage(imageModel: ImageModel)
}

internal inline fun onImageDSL(crossinline imageCallback: (image: ImageModel) -> Unit) = object : onImagePicked {
    override fun forImage(imageModel: ImageModel) {
        imageCallback(imageModel)
    }
}