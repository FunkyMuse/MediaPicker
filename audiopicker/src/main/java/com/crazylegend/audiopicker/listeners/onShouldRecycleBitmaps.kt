package com.crazylegend.audiopicker.listeners


/**
 * Created by crazy on 5/15/20 to long live and prosper !
 */
interface onShouldRecycleBitmaps {
    fun recycle()
}

/**
 * Functional interfaces where you at when you need them....
 * save me from writing this hell all the time pls
 */
fun recycleBitmapsDSL(action:()->Unit = {}) = object  : onShouldRecycleBitmaps {
    override fun recycle() {
        action()
    }
}