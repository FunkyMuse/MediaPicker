package com.crazylegend.core

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */

fun AppCompatImageView.loadImage(uri: Uri) {
    Glide.with(this)
            .load(uri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(this)
}

fun Cursor.getSafeColumn(column: String): Int? {
    return try {
        getColumnIndexOrThrow(column)
    } catch (e: Exception) {
        null
    }
}

fun <T : Any> Cursor.mapToList(cursorPredicate: (Cursor) -> T): List<T> = generateSequence {
    if (moveToNext()) cursorPredicate(this) else null
}.toList()


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

fun ContentResolver.registerObserver(
    uri: Uri,
    observer: (change: Boolean) -> Unit
): ContentObserver {
    val contentObserver = object : ContentObserver(Handler()) {
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