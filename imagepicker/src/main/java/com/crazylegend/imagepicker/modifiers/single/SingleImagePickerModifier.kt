package com.crazylegend.imagepicker.modifiers.single

import android.os.Parcelable
import com.crazylegend.imagepicker.modifiers.ImageTextModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SingleImagePickerModifier(
        val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
        val titleTextModifier: ImageTextModifier = ImageTextModifier()
) : Parcelable {

    inline fun setup(imageText: ImageTextModifier.() -> Unit= {}, closeButton: CloseButtonModifier.() -> Unit = {}) {
        titleTextModifier.imageText()
        closeButtonModifier.closeButton()
    }
}