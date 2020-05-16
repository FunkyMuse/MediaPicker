package com.crazylegend.audiopicker.modifiers

import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.core.modifiers.multi.DoneButtonModifier
import com.crazylegend.core.modifiers.multi.SelectIconModifier
import com.crazylegend.core.modifiers.single.ImageModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/14/20 to long live and prosper !
 */

@Parcelize
data class MultiAudioPickerModifier(
        override val doneButtonModifier: DoneButtonModifier = DoneButtonModifier(),
        override val titleTextModifier: TitleTextModifier = TitleTextModifier(),
        override val selectIconModifier: SelectIconModifier = SelectIconModifier(),
        override val unSelectedIconModifier: SelectIconModifier = SelectIconModifier(),
        override var indicatorsGravity: Gravity = Gravity.BOTTOM_RIGHT,
        override val viewHolderPlaceholderModifier: ImageModifier = ImageModifier(),
        override var loadingIndicatorTint: Int? = null,
        val viewHolderTitleTextModifier: TitleTextModifier = TitleTextModifier()
) : BaseMultiPickerModifier(doneButtonModifier, titleTextModifier, selectIconModifier, unSelectedIconModifier, indicatorsGravity, viewHolderPlaceholderModifier, loadingIndicatorTint) {

    inline fun setupViewHolderTitleText(viewHolderPlaceholderModifications: TitleTextModifier.() -> Unit = {}) {
        viewHolderTitleTextModifier.viewHolderPlaceholderModifications()
    }
}