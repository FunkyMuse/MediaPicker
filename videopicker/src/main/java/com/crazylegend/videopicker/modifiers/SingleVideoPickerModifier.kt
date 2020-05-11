package com.crazylegend.videopicker.modifiers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SingleVideoPickerModifier(
        val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
        val titleTextModifier: VideoTextModifier = VideoTextModifier()
) : Parcelable {

    inline fun setup(titleText: VideoTextModifier.() -> Unit = {}, closeButton: CloseButtonModifier.() -> Unit = {}) {
        titleTextModifier.titleText()
        closeButtonModifier.closeButton()
    }
}