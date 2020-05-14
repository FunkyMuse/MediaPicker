package com.crazylegend.imagepicker.pickers

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.extensions.setupManager
import com.crazylegend.imagepicker.consts.MULTI_PICKER_BOTTOM_SHEET
import com.crazylegend.imagepicker.consts.MULTI_PICKER_DIALOG
import com.crazylegend.imagepicker.dialogs.multi.MultiImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.dialogs.multi.MultiImagePickerDialogFragment
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.listeners.onImagesDSL


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
object MultiImagePicker {

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
    fun bottomSheetPicker(
            context: Context, multiImagePickerModifier: MultiPickerModifier.() -> Unit = {},
            imagesList: (list: List<ImageModel>) -> Unit
    ) {
        val manager = context.setupManager()
        val modifier = setupModifier(multiImagePickerModifier)
        with(MultiImagePickerBottomSheetDialog()) {
            addModifier(modifier)
            onImagesPicked = onImagesDSL(imagesList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun dialogPicker(
            context: Context, multiImagePickerModifier: MultiPickerModifier.() -> Unit = {},
            imagesList: (list: List<ImageModel>) -> Unit
    ) {
        val manager = context.setupManager()
        val modifier = setupModifier(multiImagePickerModifier)
        with(MultiImagePickerDialogFragment()) {
            addModifier(modifier)
            onImagesPicked = onImagesDSL(imagesList)
            show(manager, MULTI_PICKER_DIALOG)
        }
    }

    private inline fun setupModifier(modifier: MultiPickerModifier.() -> Unit) =
            MultiPickerModifier().also { it.modifier() }
}