package com.crazylegend.core

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.graphics.Point
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */


fun DialogFragment.setupDialogOnStart(sizeByX: Float = 0.9f) {
    val window = dialog?.window
    val size = Point()
    val display = window?.windowManager?.defaultDisplay
    display?.getSize(size)
    window?.setLayout((size.x * sizeByX).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
    window?.setGravity(Gravity.CENTER)
}

fun Context.setupManager(): FragmentManager {
    val manager = when (this) {
        is Fragment -> this.childFragmentManager
        is AppCompatActivity -> this.supportFragmentManager
        else -> null
    }
    requireNotNull(manager) {
        "Use a Fragment or AppCompat activity"
    }
    return manager
}

val AndroidViewModel.context: Context
    get() = getApplication()

val ViewGroup.inflater: LayoutInflater get() = LayoutInflater.from(context)

fun ContentResolver.registerObserver(uri: Uri, observer: (change: Boolean) -> Unit): ContentObserver {
    val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            observer(selfChange)
        }
    }
    registerContentObserver(uri, true, contentObserver)
    return contentObserver
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}