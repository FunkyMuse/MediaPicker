package com.crazylegend.imagepicker.modifiers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SingleImagePickerModifier(
        private val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
        private val videoTextModifier: ImageTextModifier = ImageTextModifier()
) : Parcelable {

    val getTextModifier get() = videoTextModifier
    val getCloseButtonModifier get() = closeButtonModifier

    operator fun invoke(imageText: ImageTextModifier.() -> Unit, close: CloseButtonModifier.() -> Unit = {}) {
        videoTextModifier.imageText()
        closeButtonModifier.close()
    }
}