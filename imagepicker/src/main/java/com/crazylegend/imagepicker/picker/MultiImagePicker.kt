package com.crazylegend.imagepicker.picker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleObserver
import com.crazylegend.core.setupManager
import com.crazylegend.imagepicker.consts.MULTI_PICKER_BOTTOM_SHEET
import com.crazylegend.imagepicker.consts.MULTI_PICKER_DIALOG
import com.crazylegend.imagepicker.dialogs.multi.MultiImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.dialogs.multi.MultiImagePickerDialogFragment
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.listeners.onImagesDSL


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
object MultiImagePicker : LifecycleObserver {

    fun restoreListener(context: Context, imagesList: (list: List<ImageModel>) -> Unit) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(MULTI_PICKER_BOTTOM_SHEET)
                ?: manager.findFragmentByTag(MULTI_PICKER_DIALOG)) {
            is MultiImagePickerDialogFragment -> {
                fragment.onImagesPicked = onImagesDSL(imagesList)
            }
            is MultiImagePickerBottomSheetDialog -> {
                fragment.onImagesPicked = onImagesDSL(imagesList)
            }
            null -> {
                Log.e(MultiImagePicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(context: Context, imagesList: (list: List<ImageModel>) -> Unit) {
        val manager = context.setupManager()
        with(MultiImagePickerBottomSheetDialog()) {
            onImagesPicked = onImagesDSL(imagesList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun dialogPicker(context: Context, imagesList: (list: List<ImageModel>) -> Unit) {
        val manager = context.setupManager()
        with(MultiImagePickerDialogFragment()) {
            onImagesPicked = onImagesDSL(imagesList)
            show(manager, MULTI_PICKER_DIALOG)
        }
    }

}