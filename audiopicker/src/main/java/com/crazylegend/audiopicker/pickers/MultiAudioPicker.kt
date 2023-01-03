package com.crazylegend.audiopicker.pickers

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.fragment.app.FragmentManager
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.consts.MULTI_PICKER_BOTTOM_SHEET
import com.crazylegend.audiopicker.consts.MULTI_PICKER_DIALOG
import com.crazylegend.audiopicker.dialogs.multi.MultiAudioPickerBottomSheetDialog
import com.crazylegend.audiopicker.listeners.onAudiosDSL
import com.crazylegend.audiopicker.modifiers.MultiAudioPickerModifier
import com.crazylegend.extensions.setupManager


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
object MultiAudioPicker {


    const val MULTI_AUDIO_REQUEST_KEY = "multiAudiosRequest"
    const val ON_MULTI_AUDIO_PICK_KEY = "onMultiAudiosPicked"

    fun restoreListener(context: Context, audioList: (list: List<AudioModel>) -> Unit = {}) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(MULTI_PICKER_BOTTOM_SHEET)
                ?: manager.findFragmentByTag(MULTI_PICKER_DIALOG)) {
            is MultiAudioPickerBottomSheetDialog -> {
                fragment.onAudiosPicked = onAudiosDSL(audioList)
            }
            null -> {
                Log.e(MultiAudioPicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }


    fun showPicker(context: Context, modifier: MultiAudioPickerModifier.() -> Unit = {}, audioList: (list: List<AudioModel>) -> Unit = {}) {
        val manager = context.setupManager()
        val setupModifier = setupModifier(modifier)
        with(MultiAudioPickerBottomSheetDialog()) {
            addModifier(setupModifier)
            onAudiosPicked = onAudiosDSL(audioList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    fun showPicker(fragmentManager: FragmentManager, modifier: MultiAudioPickerModifier.() -> Unit = {}, audioList: (list: List<AudioModel>) -> Unit = {}) {
        val setupModifier = setupModifier(modifier)
        with(MultiAudioPickerBottomSheetDialog()) {
            addModifier(setupModifier)
            onAudiosPicked = onAudiosDSL(audioList)
            show(fragmentManager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    private inline fun setupModifier(modifier: MultiAudioPickerModifier.() -> Unit) =
            MultiAudioPickerModifier().also(modifier)
}