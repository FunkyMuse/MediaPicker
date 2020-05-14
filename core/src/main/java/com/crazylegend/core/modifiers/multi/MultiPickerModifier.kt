package com.crazylegend.core.modifiers.multi

import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.crazylegend.core.*
import com.crazylegend.core.modifiers.TitleTextModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */
@Parcelize
data class MultiPickerModifier(
        val doneButtonModifier: DoneButtonModifier = DoneButtonModifier(),
        val titleTextModifier: TitleTextModifier = TitleTextModifier(),
        val selectIconModifier: SelectIconModifier = SelectIconModifier(),
        val unSelectedIconModifier: SelectIconModifier = SelectIconModifier(),
        var indicatorsGravity: Gravity = Gravity.BOTTOM_RIGHT
) : Parcelable {


    fun applyGravity(imageView: AppCompatImageView){
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

    fun applyGravityWithBottomConstraint(imageView: AppCompatImageView, view:View){
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

    enum class Gravity {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    inline fun setup(
        gravityForIndicators: Gravity = Gravity.TOP_RIGHT,
        titleText: TitleTextModifier.() -> Unit = {},
        doneButton: DoneButtonModifier.() -> Unit = {},
        selectIcon: SelectIconModifier.() -> Unit = {},
        unSelectIcon: SelectIconModifier.() -> Unit = {}) {
        indicatorsGravity = gravityForIndicators
        titleTextModifier.titleText()
        doneButtonModifier.doneButton()
        selectIconModifier.selectIcon()
        unSelectedIconModifier.unSelectIcon()
    }
}