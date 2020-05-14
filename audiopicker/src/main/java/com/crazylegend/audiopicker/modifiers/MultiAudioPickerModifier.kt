package com.crazylegend.audiopicker.modifiers

import android.os.Parcelable
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.core.modifiers.single.ImageButtonModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/14/20 to long live and prosper !
 */

@Parcelize
data class MultiAudioPickerModifier(
        val multiPickerModifier: MultiPickerModifier = MultiPickerModifier(),
        val viewHolderTitleModifier: TitleTextModifier = TitleTextModifier(),
        val viewHolderPlaceholderModifier: ImageButtonModifier = ImageButtonModifier()
) : Parcelable {

    inline fun setupMultiAudioPicker(multiPicker: MultiPickerModifier.() -> Unit = {}, viewHolderTitle: TitleTextModifier.() -> Unit = {},
                                     viewHolderPlaceHolder: ImageButtonModifier.() -> Unit = {}) {
        viewHolderPlaceholderModifier.viewHolderPlaceHolder()
        multiPickerModifier.multiPicker()
        viewHolderTitleModifier.viewHolderTitle()
    }
}