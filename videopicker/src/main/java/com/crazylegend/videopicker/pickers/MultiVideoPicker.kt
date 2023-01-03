package com.crazylegend.videopicker.pickers

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.fragment.app.FragmentManager
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.core.setupModifier
import com.crazylegend.extensions.setupManager
import com.crazylegend.videopicker.consts.MULTI_PICKER_BOTTOM_SHEET
import com.crazylegend.videopicker.consts.MULTI_PICKER_DIALOG
import com.crazylegend.videopicker.dialogs.multi.MultiVideoPickerBottomSheetDialog
import com.crazylegend.videopicker.listeners.onVideosDSL
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */

object MultiVideoPicker {

    const val MULTI_VIDEO_REQUEST_KEY = "multiVideosRequest"
    const val ON_MULTI_VIDEO_PICK_KEY = "onMultiVideosPicked"

    fun restoreListener(context: Context, videoList: (list: List<VideoModel>) -> Unit = {}) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(MULTI_PICKER_BOTTOM_SHEET)
                ?: manager.findFragmentByTag(MULTI_PICKER_DIALOG)) {
            is MultiVideoPickerBottomSheetDialog -> {
                fragment.onVideosPicked = onVideosDSL(videoList)
            }
            null -> {
                Log.e(MultiVideoPicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    fun showPicker(context: Context, modifier: BaseMultiPickerModifier.() -> Unit = {}, videoList: (list: List<VideoModel>) -> Unit = {}) {
        val manager = context.setupManager()
        val setupModifier = setupModifier(modifier)
        with(MultiVideoPickerBottomSheetDialog()) {
            addModifier(setupModifier)
            onVideosPicked = onVideosDSL(videoList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    fun showPicker(fragmentManager: FragmentManager, modifier: BaseMultiPickerModifier.() -> Unit = {}, videoList: (list: List<VideoModel>) -> Unit = {}) {
        val setupModifier = setupModifier(modifier)
        with(MultiVideoPickerBottomSheetDialog()) {
            addModifier(setupModifier)
            onVideosPicked = onVideosDSL(videoList)
            show(fragmentManager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

}