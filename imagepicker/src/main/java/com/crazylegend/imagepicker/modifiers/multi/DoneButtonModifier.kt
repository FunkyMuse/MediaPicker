package com.crazylegend.imagepicker.modifiers.multi

import android.content.res.ColorStateList
import android.os.Parcelable
import android.widget.RelativeLayout
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import com.google.android.material.button.MaterialButton
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */

@Parcelize
data class DoneButtonModifier(
        var padding: Int? = null,
        var startMargin: Int? = null,
        var endMargin: Int? = null,
        var marginTop: Int? = null,
        var marginBottom: Int? = null,
        var margin: Int? = null,
        var tint: Int? = null,
        var cornerRadius: Int? = null,
        var rippleColor: Int? = null,
        var icon: Int? = null,
        var iconGravity: Int? = null,
        var iconTint: Int? = null
) : Parcelable {

    private val allSizeMarginCondition get() = margin != null

    fun applyImageParams(button: MaterialButton) {
        padding?.let { button.setPadding(it) }
        if (allSizeMarginCondition) {
            updateAllMargins(button)
        } else {
            updateMargins(button)
        }
        iconTint?.let { button.iconTint = ColorStateList.valueOf(it) }
        iconGravity?.let { button.iconGravity = it }
        icon?.let { button.setIconResource(it) }
        rippleColor?.let { button.rippleColor = ColorStateList.valueOf(it) }
        cornerRadius?.let { button.cornerRadius = it }
        tint?.let { button.backgroundTintList = ColorStateList.valueOf(it) }
    }

    private fun updateMargins(button: MaterialButton) {
        button.updateLayoutParams<RelativeLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateAllMargins(button: MaterialButton) {
        button.updateLayoutParams<RelativeLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }
}