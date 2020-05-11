package com.crazylegend.imagepicker.modifiers

import android.os.Parcelable
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
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
data class CloseButtonModifier(
    var resID: Int? = null,
    var padding: Int? = null,
    var startMargin: Int? = null,
    var endMargin: Int? = null,
    var marginTop: Int? = null,
    var marginBottom: Int? = null,
    var margin: Int? = null
) : Parcelable {

    private val allSizeMarginCondition get() = margin != null

    fun applyImageParams(imageView: AppCompatImageView){
        resID?.let { imageView.setImageResource(it) }
        padding?.let { imageView.setPadding(it) }
        if (allSizeMarginCondition){
            updateAllMargins(imageView)
        } else {
            updateMargins(imageView)
        }
    }

    private fun updateMargins(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<RelativeLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateAllMargins(imageView: AppCompatImageView) {
        imageView.updateLayoutParams<RelativeLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }
}