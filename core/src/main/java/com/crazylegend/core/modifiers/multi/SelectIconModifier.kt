package com.crazylegend.core.modifiers.multi

import android.content.res.ColorStateList
import android.os.Parcelable
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import androidx.core.widget.ImageViewCompat
import kotlinx.parcelize.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */

@Parcelize
data class SelectIconModifier(
        var resID: Int? = null,
        var padding: Int? = null,
        var startMargin: Int? = null,
        var endMargin: Int? = null,
        var marginTop: Int? = null,
        var marginBottom: Int? = null,
        var margin: Int? = null,
        var tint: Int? = null
) : Parcelable {

    private val allSizeMarginCondition get() = margin != null


    fun applyImageParams(imageView: AppCompatImageView, resourceID: Int) {
        imageView.setImageResource(resourceID)
        padding?.let { imageView.setPadding(it) }
        tint?.let { ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(it)) }

        if (allSizeMarginCondition) {
            updateAllMargins(imageView)
        } else {
            updateMargins(imageView)
        }
    }

    private fun updateMargins(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateAllMargins(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }
}