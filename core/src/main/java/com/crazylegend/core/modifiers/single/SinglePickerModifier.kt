package com.crazylegend.core.modifiers.single

import android.os.Parcelable
import com.crazylegend.core.modifiers.TitleTextModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SinglePickerModifier(
        val closeButtonModifier: CloseButtonModifier = CloseButtonModifier(),
        val titleTextModifier: TitleTextModifier = TitleTextModifier()
) : Parcelable {

    inline fun setup(imageText: TitleTextModifier.() -> Unit = {}, closeButton: CloseButtonModifier.() -> Unit = {}) {
        titleTextModifier.imageText()
        closeButtonModifier.closeButton()
    }

    inline fun setup(imageText: TitleTextModifier.() -> Unit = {}) {
        titleTextModifier.imageText()
    }

}