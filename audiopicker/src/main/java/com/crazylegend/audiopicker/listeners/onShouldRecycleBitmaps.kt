package com.crazylegend.audiopicker.listeners


/**
 * Created by crazy on 5/15/20 to long live and prosper !
 */
internal interface onShouldRecycleBitmaps {
    fun keepItClean()
}

internal inline fun recycleBitmapsDSL(crossinline recycle:()->Unit = {}) = object : onShouldRecycleBitmaps {
    override fun keepItClean() {
        recycle()
    }
}