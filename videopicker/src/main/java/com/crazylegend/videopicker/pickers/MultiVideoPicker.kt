package com.crazylegend.videopicker.pickers

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.core.setupManager
import com.crazylegend.videopicker.consts.MULTI_PICKER_BOTTOM_SHEET
import com.crazylegend.videopicker.consts.MULTI_PICKER_DIALOG
import com.crazylegend.videopicker.dialogs.multi.MultiVideoPickerBottomSheetDialog
import com.crazylegend.videopicker.dialogs.multi.MultiVideoPickerDialogFragment
import com.crazylegend.videopicker.listeners.onVideosDSL

import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */

object MultiVideoPicker {

    fun restoreListener(context: Context, videoList: (list: List<VideoModel>) -> Unit) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(MULTI_PICKER_BOTTOM_SHEET)
                ?: manager.findFragmentByTag(MULTI_PICKER_DIALOG)) {
            is MultiVideoPickerBottomSheetDialog -> {
                fragment.onVideosPicked = onVideosDSL(videoList)
            }
            is MultiVideoPickerDialogFragment -> {
                fragment.onVideosPicked = onVideosDSL(videoList)
            }
            null -> {
                Log.e(MultiVideoPicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(context: Context, modifier: MultiPickerModifier.() -> Unit ={}, videoList: (list: List<VideoModel>) -> Unit) {
        val manager = context.setupManager()
        val setupModifier = setupModifier(modifier)
        with(MultiVideoPickerBottomSheetDialog()) {
            addModifier(setupModifier)
            onVideosPicked = onVideosDSL(videoList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun dialogPicker(context: Context, modifier: MultiPickerModifier.() -> Unit ={}, videoList: (list: List<VideoModel>) -> Unit) {
        val setupModifier = setupModifier(modifier)
        val manager = context.setupManager()
        with(MultiVideoPickerDialogFragment()) {
            addModifier(setupModifier)
            onVideosPicked = onVideosDSL(videoList)
            show(manager, MULTI_PICKER_DIALOG)
        }
    }


    private inline fun setupModifier(modifier: MultiPickerModifier.() -> Unit) =
        MultiPickerModifier().also { it.modifier() }
}