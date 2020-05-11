package com.crazylegend.extensions

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */


fun Cursor.getSafeColumn(column: String): Int? {
    return try {
        getColumnIndexOrThrow(column)
    } catch (e: Exception) {
        null
    }
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