package com.crazylegend.audiopicker.contracts

import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.listeners.onAudioPicked
import com.crazylegend.audiopicker.modifiers.SingleAudioPickerModifier
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.adapters.single.SingleAdapter
import com.crazylegend.core.contracts.BaseContractSinglePick
import com.crazylegend.core.modifiers.base.BaseSinglePickerModifier


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
internal interface SinglePickerContracts : BaseContractSinglePick {
    val audiosVM: AudiosVM
    var onAudioPicked: onAudioPicked?
    val errorTag: String get() = SingleAudioPicker::javaClass.name

    override val modifier: SingleAudioPickerModifier?
    override val singleAdapter: SingleAdapter?
        get() = null

    override fun addModifier(modifier: BaseSinglePickerModifier) {}
    fun recycleBitmaps()

    fun addModifier(modifier: SingleAudioPickerModifier)
}