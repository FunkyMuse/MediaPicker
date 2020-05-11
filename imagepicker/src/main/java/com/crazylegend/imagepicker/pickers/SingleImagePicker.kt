package com.crazylegend.imagepicker.pickers

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.crazylegend.core.modifiers.single.SinglePickerModifier
import com.crazylegend.extensions.setupManager
import com.crazylegend.imagepicker.consts.SINGLE_PICKER_BOTTOM_SHEET
import com.crazylegend.imagepicker.consts.SINGLE_PICKER_DIALOG
import com.crazylegend.imagepicker.dialogs.single.SingleImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.dialogs.single.SingleImagePickerDialogFragment
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.listeners.onImageDSL



/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
object SingleImagePicker {

    fun restoreListener(context: Context, onPickedImage: (image: ImageModel) -> Unit) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(SINGLE_PICKER_BOTTOM_SHEET)
            ?: manager.findFragmentByTag(SINGLE_PICKER_DIALOG)) {
            is SingleImagePickerBottomSheetDialog -> {
                fragment.onImagePicked = onImageDSL(onPickedImage)
            }
            is SingleImagePickerDialogFragment -> {
                fragment.onImagePicked = onImageDSL(onPickedImage)
            }
            null -> {
                Log.e(SingleImagePicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(
            context: Context,
            singleImagePickerModifier: SinglePickerModifier.() -> Unit = {},
            onPickedImage: (image: ImageModel) -> Unit
    ) {
        val modifier = setupModifier(singleImagePickerModifier)
        val manager = context.setupManager()
        with(SingleImagePickerBottomSheetDialog()) {
            addModifier(modifier)
            onImagePicked = onImageDSL(onPickedImage)
            show(manager, SINGLE_PICKER_BOTTOM_SHEET)
        }
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun dialogPicker(
        context: Context,
        singleImagePickerModifier: SinglePickerModifier.() -> Unit = {},
        onPickedImage: (image: ImageModel) -> Unit
    ) {
        val modifier = setupModifier(singleImagePickerModifier)
        val manager = context.setupManager()
        with(SingleImagePickerDialogFragment()) {
            addModifier(modifier)
            onImagePicked = onImageDSL(onPickedImage)
            show(manager, SINGLE_PICKER_DIALOG)
        }
    }

    private inline fun setupModifier(singleImagePickerModifier: SinglePickerModifier.() -> Unit) =
        SinglePickerModifier().also { it.singleImagePickerModifier() }


}