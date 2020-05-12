package com.crazylegend.audiopicker.contracts

import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.listeners.onAudioPicked
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.contracts.BaseContractSinglePick


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
internal interface SinglePickerContracts : BaseContractSinglePick {
    val audiosVM: AudiosVM
    var onAudioPicked: onAudioPicked?
    val errorTag: String get() = SingleAudioPicker::javaClass.name
}