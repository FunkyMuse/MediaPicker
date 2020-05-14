package com.crazylegend.core.modifiers.single

import android.os.Parcelable
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */

/**
 * Only works for dialog fragment since there it's visible
 */
@Parcelize
data class ImageButtonModifier(
        var resID: Int? = null,
        var padding: Int? = null,
        var startMargin: Int? = null,
        var endMargin: Int? = null,
        var marginTop: Int? = null,
        var marginBottom: Int? = null,
        var margin: Int? = null
) : Parcelable {

    private val allSizeMarginCondition get() = margin != null

    fun applyImageParamsRelativeLayout(imageView: AppCompatImageView) {
        resID?.let { imageView.setImageResource(it) }
        padding?.let { imageView.setPadding(it) }
        if (allSizeMarginCondition) {
            updateAllMarginsRelative(imageView)
        } else {
            updateMarginsRelative(imageView)
        }
    }

    fun applyImageParamsConstraintLayout(imageView: AppCompatImageView) {
        resID?.let { imageView.setImageResource(it) }
        padding?.let { imageView.setPadding(it) }
        if (allSizeMarginCondition) {
            updateAllMarginsConstraint(imageView)
        } else {
            updateMarginsConstraint(imageView)
        }
    }


    private fun updateMarginsRelative(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<RelativeLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateMarginsConstraint(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateAllMarginsRelative(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<RelativeLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }

    private fun updateAllMarginsConstraint(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }
}