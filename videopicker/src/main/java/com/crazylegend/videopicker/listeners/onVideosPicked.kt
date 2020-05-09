package com.crazylegend.videopicker.listeners

import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface onVideosPicked {
    fun onVideosPicked(videos: List<VideoModel> = emptyList())
}

internal fun onVideosDSL(callback: (list: List<VideoModel>) -> Unit) = object : onVideosPicked {
    override fun onVideosPicked(videos: List<VideoModel>) {
        callback(videos)
    }
}