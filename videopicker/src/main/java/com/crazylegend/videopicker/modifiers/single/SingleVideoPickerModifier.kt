package com.crazylegend.videopicker.modifiers.single

import android.os.Parcelable
import com.crazylegend.videopicker.modifiers.VideoTextModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SingleVideoPickerModifier(
    val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
    val titleTextModifier: VideoTextModifier = VideoTextModifier()
) : Parcelable {

    inline fun setup(imageText: VideoTextModifier.() -> Unit= {}, closeButton: CloseButtonModifier.() -> Unit = {}) {
        titleTextModifier.imageText()
        closeButtonModifier.closeButton()
    }
}