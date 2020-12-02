package com.crazylegend.audiopicker.listeners

import com.crazylegend.audiopicker.audios.AudioModel


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
internal fun interface onAudioPicked {
    fun forAudio(audio: AudioModel)
}

internal inline fun onAudioDSL(crossinline callback: (audioModel: AudioModel) -> Unit = {}) = onAudioPicked { audio -> callback(audio) }