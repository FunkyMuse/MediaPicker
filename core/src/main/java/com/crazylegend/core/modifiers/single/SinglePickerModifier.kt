package com.crazylegend.core.modifiers.single

import android.os.Parcelable
import com.crazylegend.core.modifiers.TitleTextModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class SinglePickerModifier(
        val closeButtonModifier: ImageButtonModifier = ImageButtonModifier(),
        val titleTextModifier: TitleTextModifier = TitleTextModifier(),
        var loadingIndicatorTint: Int? = null
) : Parcelable {

    inline fun setupTitleAndCloseButton(
            loadingIndicatorColor: Int? = null, title: TitleTextModifier.() -> Unit = {}, closeButton: ImageButtonModifier.() -> Unit = {}) {
        titleTextModifier.title()
        closeButtonModifier.closeButton()
        loadingIndicatorTint = loadingIndicatorColor
    }

    inline fun setupTitleOnly(loadingIndicatorColor: Int? = null,imageText: TitleTextModifier.() -> Unit = {}) {
        titleTextModifier.imageText()
        loadingIndicatorTint = loadingIndicatorColor
    }

}