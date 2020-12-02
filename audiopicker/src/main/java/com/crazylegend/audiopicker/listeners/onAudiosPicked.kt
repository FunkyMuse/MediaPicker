package com.crazylegend.audiopicker.listeners

import com.crazylegend.audiopicker.audios.AudioModel


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
internal fun interface onAudiosPicked {
    fun forAudios(audioList: List<AudioModel>)
}

internal inline fun onAudiosDSL(crossinline callback: (audioList: List<AudioModel>) -> Unit = {}) = onAudiosPicked { audioList -> callback(audioList) }