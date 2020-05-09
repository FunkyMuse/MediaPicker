package com.crazylegend.videopicker.listeners

import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface onVideoPicked {
    fun forVideo(videoModel: VideoModel)
}

internal fun onVideoDSL(videoCallback: (video: VideoModel) -> Unit) = object : onVideoPicked {
    override fun forVideo(videoModel: VideoModel) {
        videoCallback(videoModel)
    }
}