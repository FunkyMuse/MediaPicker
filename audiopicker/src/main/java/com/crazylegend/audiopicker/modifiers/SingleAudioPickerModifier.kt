package com.crazylegend.audiopicker.modifiers

import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.base.BaseSinglePickerModifier
import com.crazylegend.core.modifiers.single.ImageModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/14/20 to long live and prosper !
 */

@Parcelize
data class SingleAudioPickerModifier(
        override val viewHolderPlaceholderModifier: ImageModifier = ImageModifier(),
        override val titleTextModifier: TitleTextModifier = TitleTextModifier(),
        override var loadingIndicatorTint: Int? = null,
        val viewHolderTitleTextModifier: TitleTextModifier = TitleTextModifier()
) : BaseSinglePickerModifier(titleTextModifier, viewHolderPlaceholderModifier, loadingIndicatorTint) {

    inline fun setupViewHolderTitleText(viewHolderPlaceholderModifications: TitleTextModifier.() -> Unit = {}) {
        viewHolderTitleTextModifier.viewHolderPlaceholderModifications()
    }

}