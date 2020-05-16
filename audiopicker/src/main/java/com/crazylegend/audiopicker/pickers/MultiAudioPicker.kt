package com.crazylegend.audiopicker.pickers

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
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

    fun restoreListener(context: Context, audioList: (list: List<AudioModel>) -> Unit) {
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

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(context: Context, modifier: MultiAudioPickerModifier.() -> Unit = {}, audioList: (list: List<AudioModel>) -> Unit) {
        val manager = context.setupManager()
        val setupModifier = setupModifier(modifier)
        with(MultiAudioPickerBottomSheetDialog()) {
            addModifier(setupModifier)
            onAudiosPicked = onAudiosDSL(audioList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    private inline fun setupModifier(modifier: MultiAudioPickerModifier.() -> Unit) =
            MultiAudioPickerModifier().also(modifier)
}