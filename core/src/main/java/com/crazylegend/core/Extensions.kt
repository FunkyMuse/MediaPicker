package com.crazylegend.core

import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide

/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */


internal fun AppCompatImageView.loadImage(uri: Uri) {
    Glide.with(this)
            .load(uri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(this)
}

val ViewGroup.inflater: LayoutInflater get() = LayoutInflater.from(context)




internal fun View.visible() {
    this.visibility = View.VISIBLE
}

internal fun View.gone() {
    this.visibility = View.GONE
}

internal fun View.invisible() {
    this.visibility = View.INVISIBLE
}


internal fun <T : View> T.centerInParent(): T {
    return centerHorizontally().centerVertically()
}

internal fun <T : View> T.centerHorizontally(): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            centerHorizontally(id, constraintLayout.id)
        }
    }
    return this
}

internal fun <T : View> T.centerVertically(): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            centerVertically(id, constraintLayout.id)
        }
    }
    return this
}


internal fun <T : View> T.fillParent(padding: Int = 0): T {
    return fillVertically(padding).fillHorizontally(padding)
}

internal fun <T : View> T.fillVertically(padding: Int = 0): T {
    layoutParams.height = ConstraintSet.MATCH_CONSTRAINT // Needed to "match constraints"
    return top(padding).bottom(padding)
}

internal fun <T : View> T.fillHorizontally(padding: Int = 0): T {
    layoutParams.width = ConstraintSet.MATCH_CONSTRAINT // Needed to "match constraints"
    return left(padding).right(padding)
}


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


// Top

internal fun <T : View> T.constrainTopToBottomOf(view: View, margin: Int = 0): T {
    return constrainTopToBottomOf(view.id, margin)
}

internal fun <T : View> T.constrainTopToBottomOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.TOP, viewId, ConstraintLayout.LayoutParams.BOTTOM, margin.dp)
    }
    return this
}

internal fun <T : View> T.constrainTopToTopOf(view: View, margin: Int = 0): T {
    return constrainTopToTopOf(view.id, margin)
}

internal fun <T : View> T.constrainTopToTopOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.TOP, viewId, ConstraintLayout.LayoutParams.TOP, margin.dp)
    }
    return this
}


// Left

internal fun <T : View> T.constrainLeftToRightOf(view: View, margin: Int = 0): T {
    return constrainLeftToRightOf(view.id, margin)
}

internal fun <T : View> T.constrainLeftToRightOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.LEFT, viewId, ConstraintLayout.LayoutParams.RIGHT, margin.dp)
    }

    return this
}

internal fun <T : View> T.constrainLeftToLeftOf(view: View, margin: Int = 0): T {
    return constrainLeftToLeftOf(view.id, margin)
}

internal fun <T : View> T.constrainLeftToLeftOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.LEFT, viewId, ConstraintLayout.LayoutParams.LEFT, margin.dp)
    }

    return this
}

// Right

internal fun <T : View> T.constrainRightToLeftOf(view: View, margin: Int = 0): T {
    return constrainRightToLeftOf(view.id, margin)
}

internal fun <T : View> T.constrainRightToLeftOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.RIGHT, viewId, ConstraintLayout.LayoutParams.LEFT, margin.dp)
    }

    return this
}

internal fun <T : View> T.constrainRightToRightOf(view: View, margin: Int = 0): T {
    return constrainRightToRightOf(view.id, margin)
}

internal fun <T : View> T.constrainRightToRightOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.RIGHT, viewId, ConstraintLayout.LayoutParams.RIGHT, margin.dp)
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

internal fun <T : View> T.constrainBottomToBottomOf(view: View, margin: Int = 0): T {
    return constrainBottomToBottomOf(view.id, margin)
}

internal fun <T : View> T.constrainBottomToBottomOf(viewId: Int, margin: Int = 0): T {
    (parent as? ConstraintLayout)?.addConstraints {
        connect(id, ConstraintLayout.LayoutParams.BOTTOM, viewId, ConstraintLayout.LayoutParams.BOTTOM, margin.dp)
    }
    return this
}


// Center Y

// This is made possible by creating a "GONE" guideline and center on the guideline instead :)
internal fun <T : View> T.constrainCenterYToBottomOf(view: View): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            val guideline = View(context)
            guideline.id = View.generateViewId()
            constraintLayout.addView(guideline)
            guideline.visibility = View.GONE
            guideline.constrainBottomToBottomOf(view)
            centerVertically(id, guideline.id)
        }
    }
    return this
}

internal fun <T : View> T.constrainCenterYToTopOf(view: View): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            val guideline = View(context)
            guideline.id = View.generateViewId()
            constraintLayout.addView(guideline)
            guideline.visibility = View.GONE
            guideline.constrainTopToTopOf(view)
            centerVertically(id, guideline.id)
        }
    }
    return this
}

internal fun <T : View> T.constrainCenterYToCenterYOf(view: View): T {
    (parent as? ConstraintLayout)?.addConstraints {
        centerVertically(id, view.id)
    }
    return this
}


// Center X

internal fun <T : View> T.constrainCenterXToLeftOf(view: View): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            val guideline = View(context)
            guideline.id = View.generateViewId()
            constraintLayout.addView(guideline)
            guideline.visibility = View.GONE
            guideline.constrainLeftToLeftOf(view)
            centerHorizontally(id, guideline.id)
        }
    }
    return this
}

internal fun <T : View> T.constrainCenterXToRightOf(view: View): T {
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            val guideline = View(context)
            guideline.id = View.generateViewId()
            constraintLayout.addView(guideline)
            guideline.visibility = View.GONE
            guideline.constrainRightToRightOf(view)
            centerHorizontally(id, guideline.id)
        }
    }
    return this
}


internal fun <T : View> T.constrainCenterXToCenterXOf(view: View): T {
    (parent as? ConstraintLayout)?.addConstraints {
        centerHorizontally(id, view.id)
    }
    return this
}


// Follow Edges

internal fun <T : View> T.followEdgesOf(view: View): T {
    constrainTopToTopOf(view)
    constrainBottomToBottomOf(view)
    constrainLeftToLeftOf(view)
    constrainRightToRightOf(view)
    return this
}


// Layout - Size

internal fun <T : View> T.size(value: Int): T {
    return width(value).height(value)
}

internal fun <T : View> T.width(value: Int): T {
    return width(value.toFloat())
}

internal fun <T : View> T.height(value: Int): T {
    return height(value.toFloat())
}

internal fun <T : View> T.width(value: Float): T {

    if (value.toInt() == ConstraintSet.MATCH_CONSTRAINT) {
        layoutParams.width = value.toInt()
        return this
    }

    if (parent is ConstraintLayout) {
        (parent as? ConstraintLayout)?.let { constraintLayout ->
            constraintLayout.addConstraints {
                constrainWidth(id, value.dp.toInt())
            }
        }
    } else {
        if (layoutParams == null) {
            layoutParams = ViewGroup.LayoutParams(ConstraintSet.WRAP_CONTENT, ConstraintSet.WRAP_CONTENT)
        }

        if (layoutParams != null) {
            layoutParams.width = if (value > 0) value.dp.toInt() else value.toInt()
        } else {

            print("NULL")
        }
    }
    return this
}

internal fun <T : View> T.height(value: Float): T {

    if (value.toInt() == ConstraintSet.MATCH_CONSTRAINT) {
        layoutParams.height = value.toInt()
        return this
    }

    if (parent is ConstraintLayout) {
        (parent as? ConstraintLayout)?.let { constraintLayout ->
            constraintLayout.addConstraints {
                constrainHeight(id, value.dp.toInt())
            }
        }
    } else {

        if (layoutParams == null) {
            layoutParams = ViewGroup.LayoutParams(ConstraintSet.WRAP_CONTENT, ConstraintSet.WRAP_CONTENT)
        }

        if (layoutParams != null) {
            layoutParams.height = if (value > 0) value.dp.toInt() else value.toInt()
        }
    }
    return this
}


// Percent Size

internal fun <T : View> T.percentWidth(value: Float): T {
    layoutParams.width = ConstraintSet.MATCH_CONSTRAINT
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            constrainPercentWidth(id, value)
        }
    }
    return this
}

internal fun <T : View> T.percentHeight(value: Float): T {
    layoutParams.height = ConstraintSet.MATCH_CONSTRAINT
    (parent as? ConstraintLayout)?.let { constraintLayout ->
        constraintLayout.addConstraints {
            constrainPercentHeight(id, value)
        }
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