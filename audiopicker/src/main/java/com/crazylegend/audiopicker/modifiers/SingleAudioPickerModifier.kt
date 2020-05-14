package com.crazylegend.audiopicker.modifiers

import android.os.Parcelable
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.single.ImageButtonModifier
import com.crazylegend.core.modifiers.single.SinglePickerModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/14/20 to long live and prosper !
 */

@Parcelize
data class SingleAudioPickerModifier(
        val singlePickerModifier: SinglePickerModifier = SinglePickerModifier(),
        val viewHolderTitleModifier: TitleTextModifier = TitleTextModifier(),
        val viewHolderPlaceholderModifier: ImageButtonModifier = ImageButtonModifier()
) : Parcelable {

    inline fun setupSingleAudioPicker(singlePicker: SinglePickerModifier.() -> Unit = {}, viewHolderTitle: TitleTextModifier.() -> Unit = {},
                                      viewHolderPlaceHolder: ImageButtonModifier.() -> Unit = {}) {
        viewHolderPlaceholderModifier.viewHolderPlaceHolder()
        singlePickerModifier.singlePicker()
        viewHolderTitleModifier.viewHolderTitle()
    }
}