package com.crazylegend.audiopicker.contracts

import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.listeners.onAudiosPicked
import com.crazylegend.audiopicker.pickers.MultiAudioPicker
import com.crazylegend.core.contracts.BaseContractMultiPick


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */

internal interface MultiPickerContracts : BaseContractMultiPick {
    val audiosVM: AudiosVM
    var onAudiosPicked: onAudiosPicked?
    val errorTag : String get() = MultiAudioPicker::javaClass.name

}