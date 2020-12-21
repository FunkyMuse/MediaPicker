package com.crazylegend.core

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.core.modifiers.base.BaseSinglePickerModifier

/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */


internal inline fun AppCompatImageView.loadImage(uri: Uri, crossinline onLoadFailed: () -> Unit = {}) {
    Glide.with(this)
            .load(uri)
            .thumbnail(0.33f)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    onLoadFailed()
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }
            })
            .centerCrop()
            .into(this)
}

internal inline fun AppCompatImageView.loadWholeImage(uri: Uri, crossinline onLoadFailed: () -> Unit = {}) {
    Glide.with(this)
            .load(uri)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    onLoadFailed()
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }
            })
            .centerCrop()
            .into(this)
}

inline fun setupModifier(modifier: BaseMultiPickerModifier.() -> Unit) = BaseMultiPickerModifier().also(modifier)


inline fun setupModifier(videoPicker: BaseSinglePickerModifier.() -> Unit) = BaseSinglePickerModifier().also(videoPicker)


val ViewGroup.inflater: LayoutInflater get() = LayoutInflater.from(context)


internal fun View.visible() {
    this.visibility = View.VISIBLE
}

internal fun View.gone() {
    this.visibility = View.GONE
}

internal val AndroidViewModel.context: Context
    get() = getApplication()



internal fun <T : View> T.top(margin: Int): T {
    return position(ConstraintLayout.LayoutParams.TOP, margin)
}

internal fun <T : View> T.left(margin: Int): T {
    return position(ConstraintLayout.LayoutParams.LEFT, margin)
}

internal fun <T : View> T.right(margin: Int): T {
    return position(ConstraintLayout.LayoutParams.RIGHT, margin)
}

internal fun <T : View> T.bottom(margin: Int): T {
    return position(ConstraintLayout.LayoutParams.BOTTOM, margin)
}

internal fun <T : View> T.position(position: Int, margin: Int): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            connect(id, position, ConstraintLayout.LayoutParams.PARENT_ID, position, margin.dp)
        }
    }
    return this
}

// Bottom

internal fun <T : View> T.constrainBottomToTopOf(view: View, margin: Int = 0): T {
    return constrainBottomToTopOf(view.id, margin)
}

internal fun <T : View> T.constrainBottomToTopOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.BOTTOM, viewId, ConstraintLayout.LayoutParams.TOP, margin.dp)
    }
    return this
}

internal fun ConstraintLayout.addConstraints(block: ConstraintSet.() -> Unit) {
    val cs = ConstraintSet()
    cs.clone(this)
    block(cs)
    cs.applyTo(this)
}


internal var Int.dp: Int
    get() {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
    }
    set(_) {}


internal var Float.dp: Float
    get() {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics)
    }
    set(_) {}