package com.crazylegend.videopicker.modifiers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SingleVideoPickerModifier(
        private val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
        private val textModifier: VideoTextModifier = VideoTextModifier()
) : Parcelable {

    val getTextModifier get() = textModifier
    val getCloseButtonModifier get() = closeButtonModifier

    operator fun invoke(text: VideoTextModifier.() -> Unit, close: CloseButtonModifier.() -> Unit = {}) {
        textModifier.text()
        closeButtonModifier.close()
    }
}