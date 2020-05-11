package com.crazylegend.videopicker.pickers

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.crazylegend.core.modifiers.single.SinglePickerModifier
import com.crazylegend.extensions.setupManager

import com.crazylegend.videopicker.consts.SINGLE_PICKER_BOTTOM_SHEET
import com.crazylegend.videopicker.consts.SINGLE_PICKER_DIALOG
import com.crazylegend.videopicker.dialogs.single.SingleVideoPickerBottomSheetDialog
import com.crazylegend.videopicker.dialogs.single.SingleVideoPickerDialogFragment
import com.crazylegend.videopicker.listeners.onVideoDSL
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */
object SingleVideoPicker {

    fun restoreListener(context: Context, onPickedVideo: (video: VideoModel) -> Unit) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(SINGLE_PICKER_BOTTOM_SHEET)
                ?: manager.findFragmentByTag(SINGLE_PICKER_DIALOG)) {
            is SingleVideoPickerBottomSheetDialog -> {
                fragment.onVideoPicked = onVideoDSL(onPickedVideo)
            }
            is SingleVideoPickerDialogFragment -> {
                fragment.onVideoPicked = onVideoDSL(onPickedVideo)
            }
            null -> {
                Log.e(SingleVideoPicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(context: Context, pickerModifier: SinglePickerModifier.()->Unit = {}, onPickedVideo: (video: VideoModel) -> Unit) {
        val modifier = setupModifier(pickerModifier)
        val manager = context.setupManager()
        with(SingleVideoPickerBottomSheetDialog()) {
            addModifier(modifier)
            onVideoPicked = onVideoDSL(onPickedVideo)
            show(manager, SINGLE_PICKER_BOTTOM_SHEET)
        }
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun dialogPicker(context: Context, pickerModifier: SinglePickerModifier.()->Unit = {}, onPickedVideo: (video: VideoModel) -> Unit) {
        val manager = context.setupManager()
        val modifier = setupModifier(pickerModifier)
        with(SingleVideoPickerDialogFragment()) {
            addModifier(modifier)
            onVideoPicked = onVideoDSL(onPickedVideo)
            show(manager, SINGLE_PICKER_DIALOG)
        }
    }

    private fun setupModifier(videoPicker: SinglePickerModifier.() -> Unit): SinglePickerModifier {
        val modifier = SinglePickerModifier()
        modifier.videoPicker()
        return modifier
    }
}