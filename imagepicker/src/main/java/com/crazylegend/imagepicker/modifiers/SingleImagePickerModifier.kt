package com.crazylegend.imagepicker.modifiers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SingleImagePickerModifier(
        private val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
        private val textModifier: TextModifier = TextModifier()
) : Parcelable {

    val getTextModifier get() = textModifier
    val getCloseButtonModifier get() = closeButtonModifier

    operator fun invoke(text: TextModifier.() -> Unit, close: CloseButtonModifier.() -> Unit = {}) {
        textModifier.text()
        closeButtonModifier.close()
    }
}