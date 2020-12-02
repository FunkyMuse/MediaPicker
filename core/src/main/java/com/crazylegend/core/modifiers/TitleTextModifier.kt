package com.crazylegend.core.modifiers

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Parcelable
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import kotlinx.parcelize.Parcelize


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


@Parcelize
data class TitleTextModifier(
        var textColor: Int? = null,
        var textPadding: Int? = null,
        var textSize: Float? = null,
        var textString: String? = null,
        var startMargin: Int? = null,
        var endMargin: Int? = null,
        var marginTop: Int? = null,
        var marginBottom: Int? = null,
        var margin: Int? = null,
        var textStyle: TextStyle = TextStyle.NORMAL,
        var textAlignment: Int = TextView.TEXT_ALIGNMENT_VIEW_START
) : Parcelable {


    private val allSizeMarginCondition get() = margin != null

    enum class TextStyle {
        BOLD, UNDERLINED, ITALIC, BOLD_ITALIC, NORMAL
    }

    private fun updateMargins(textView: AppCompatTextView) {
        textView.updateLayoutParams<RelativeLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateMarginsConstraint(textView: AppCompatTextView) {
        textView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            startMargin?.let { marginStart = it }
            endMargin?.let { marginEnd = it }
            marginTop?.let { topMargin = it }
            marginBottom?.let { bottomMargin = it }
        }
    }

    private fun updateAllMargins(textView: AppCompatTextView) {
        textView.updateLayoutParams<RelativeLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }

    private fun updateAllMarginsConstraint(textView: AppCompatTextView) {
        textView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            margin?.let { setMargins(it) }
        }
    }

    fun applyTextParams(text: AppCompatTextView) {
        textSize?.let { text.textSize = it }
        textPadding?.let { text.setPadding(it) }
        textColor?.let { text.setTextColor(it) }
        text.textAlignment = textAlignment
        applyTextStyle(text)
        textString?.let { text.text = it }
        if (allSizeMarginCondition) {
            updateAllMargins(text)
        } else {
            updateMargins(text)
        }
    }

    fun applyTextParamsConstraint(text: AppCompatTextView) {
        textSize?.let { text.textSize = it }
        textPadding?.let { text.setPadding(it) }
        textColor?.let { text.setTextColor(it) }
        text.textAlignment = textAlignment
        applyTextStyle(text)
        textString?.let { text.text = it }
        if (allSizeMarginCondition) {
            updateAllMarginsConstraint(text)
        } else {
            updateMarginsConstraint(text)
        }
    }


    private fun applyTextStyle(text: AppCompatTextView) {
        when (textStyle) {
            TextStyle.BOLD -> {
                text.setTypeface(text.typeface, Typeface.BOLD)
            }
            TextStyle.UNDERLINED -> {
                text.paintFlags = text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
            TextStyle.ITALIC -> {
                text.setTypeface(text.typeface, Typeface.ITALIC)
            }
            TextStyle.BOLD_ITALIC -> {
                text.setTypeface(text.typeface, Typeface.BOLD_ITALIC)
            }
            else -> {

            }
        }
    }
}