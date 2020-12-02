package com.crazylegend.imagepicker.listeners

import com.crazylegend.imagepicker.images.ImageModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal fun interface onImagePicked {
    fun forImage(imageModel: ImageModel)
}

internal inline fun onImageDSL(crossinline imageCallback: (image: ImageModel) -> Unit) = onImagePicked { imageModel -> imageCallback(imageModel) }