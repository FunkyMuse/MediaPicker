package com.crazylegend.imagepicker.modifiers.multi

import android.os.Parcelable
import androidx.appcompat.widget.AppCompatImageView
import com.crazylegend.core.bottom
import com.crazylegend.core.left
import com.crazylegend.core.right
import com.crazylegend.core.top
import com.crazylegend.imagepicker.modifiers.ImageTextModifier
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */
@Parcelize
data class MultiImagePickerModifier(
        val doneButtonModifier: DoneButtonModifier = DoneButtonModifier(),
        val titleTextModifier: ImageTextModifier = ImageTextModifier(),
        val selectIconModifier: SelectIconModifier = SelectIconModifier(),
        val unSelectedIconModifier: SelectIconModifier = SelectIconModifier(),
        var indicatorsGravity: Gravity = Gravity.BOTTOM_RIGHT
) : Parcelable {


    fun applyGravity(imageView: AppCompatImageView){
        when (indicatorsGravity) {
            Gravity.TOP_LEFT -> {
                imageView.top(0)
                imageView.left(0)
            }
            Gravity.TOP_RIGHT -> {
                imageView.top(0)
                imageView.right(0)
            }
            Gravity.BOTTOM_LEFT -> {
                imageView.bottom(0)
                imageView.left(0)
            }
            Gravity.BOTTOM_RIGHT -> {
                imageView.bottom(0)
                imageView.right(0)
            }
        }
    }

    enum class Gravity {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    inline fun setup(
        gravityForIndicators: Gravity = Gravity.TOP_RIGHT,
        titleText: ImageTextModifier.() -> Unit = {},
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