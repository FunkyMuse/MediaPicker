package com.crazylegend.core.modifiers.base

import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.crazylegend.core.bottom
import com.crazylegend.core.constrainBottomToTopOf
import com.crazylegend.core.left
import com.crazylegend.core.modifiers.SizeTextModifier
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.multi.DoneButtonModifier
import com.crazylegend.core.modifiers.multi.SelectIconModifier
import com.crazylegend.core.modifiers.single.ImageModifier
import com.crazylegend.core.right
import com.crazylegend.core.top
import kotlinx.parcelize.Parcelize


/**
 * Created by crazy on 5/16/20 to long live and prosper !
 */

@Parcelize
open class BaseMultiPickerModifier(
        open val doneButtonModifier: DoneButtonModifier = DoneButtonModifier(),
        open val titleTextModifier: TitleTextModifier = TitleTextModifier(),
        open val selectIconModifier: SelectIconModifier = SelectIconModifier(),
        open val unSelectedIconModifier: SelectIconModifier = SelectIconModifier(),
        open var indicatorsGravity: Gravity = Gravity.BOTTOM_RIGHT,
        open val viewHolderPlaceholderModifier: ImageModifier = ImageModifier(),
        open val noContentTextModifier: TitleTextModifier = TitleTextModifier(),
        open var loadingIndicatorTint: Int? = null,
        open val sizeTextModifier: SizeTextModifier = SizeTextModifier(),
) : Parcelable {

    enum class Gravity {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    inline fun setupBaseMultiPicker(
            doneButtonModifications: DoneButtonModifier.() -> Unit = {},
            titleModifications: TitleTextModifier.() -> Unit = {},
            selectIconModifications: SelectIconModifier.() -> Unit = {},
            unSelectIconModifications: SelectIconModifier.() -> Unit = {},
            viewHolderPlaceholderModifications: ImageModifier.() -> Unit = {},
            gravityForSelectAndUnSelectIndicators: Gravity = Gravity.BOTTOM_RIGHT,
            tintForLoadingProgressBar: Int? = null,
            sizeTextModifications: SizeTextModifier.() -> Unit = {},
    ) {
        doneButtonModifier.doneButtonModifications()
        titleTextModifier.titleModifications()
        selectIconModifier.selectIconModifications()
        unSelectedIconModifier.unSelectIconModifications()
        viewHolderPlaceholderModifier.viewHolderPlaceholderModifications()
        indicatorsGravity = gravityForSelectAndUnSelectIndicators
        loadingIndicatorTint = tintForLoadingProgressBar
        sizeTextModifier.sizeTextModifications()
    }

    fun applyGravity(imageView: AppCompatImageView) {
        when (indicatorsGravity) {
            Gravity.TOP_LEFT -> {
                imageView.top(imageView.marginTop)
                imageView.left(imageView.marginLeft)
            }

            Gravity.TOP_RIGHT -> {
                imageView.top(imageView.marginTop)
                imageView.right(imageView.marginRight)
            }

            Gravity.BOTTOM_LEFT -> {
                imageView.bottom(imageView.marginBottom)
                imageView.left(imageView.marginLeft)
            }

            Gravity.BOTTOM_RIGHT -> {
                imageView.bottom(imageView.marginBottom)
                imageView.right(imageView.marginRight)
            }
        }
    }

    fun applyGravityWithBottomConstraint(imageView: AppCompatImageView, view: View) {
        when (indicatorsGravity) {
            Gravity.TOP_LEFT -> {
                imageView.top(imageView.marginTop)
                imageView.left(imageView.marginLeft)
            }

            Gravity.TOP_RIGHT -> {
                imageView.top(imageView.marginTop)
                imageView.right(imageView.marginRight)
            }

            Gravity.BOTTOM_LEFT -> {
                imageView.constrainBottomToTopOf(view, imageView.marginBottom)
                imageView.left(imageView.marginLeft)
            }

            Gravity.BOTTOM_RIGHT -> {
                imageView.constrainBottomToTopOf(view, imageView.marginBottom)
                imageView.right(imageView.marginRight)
            }
        }
    }
}