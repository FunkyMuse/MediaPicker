package com.crazylegend.core.modifiers.base

import android.os.Parcelable
import com.crazylegend.core.modifiers.SizeTextModifier
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.single.ImageModifier
import kotlinx.parcelize.Parcelize


/**
 * Created by crazy on 5/16/20 to long live and prosper !
 */

@Parcelize
open class BaseSinglePickerModifier(
        open val titleTextModifier: TitleTextModifier = TitleTextModifier(),
        open val viewHolderPlaceholderModifier: ImageModifier = ImageModifier(),
        open val noContentTextModifier: TitleTextModifier = TitleTextModifier(),
        open var loadingIndicatorTint: Int? = null,
        open val sizeTextModifier: SizeTextModifier = SizeTextModifier(),
) : Parcelable {

    inline fun setupBaseModifier(
            loadingIndicatorColor: Int? = null,
            titleTextModifications: TitleTextModifier.() -> Unit = {},
            placeHolderModifications: ImageModifier.() -> Unit = {},
            noContentTextModifications: TitleTextModifier.() -> Unit = {},
            sizeTextModifications: SizeTextModifier.() -> Unit = {},
    ) {
        titleTextModifier.titleTextModifications()
        loadingIndicatorTint = loadingIndicatorColor
        viewHolderPlaceholderModifier.placeHolderModifications()
        noContentTextModifier.noContentTextModifications()
        sizeTextModifier.sizeTextModifications()
    }
}