package com.crazylegend.imagepicker.picker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.crazylegend.imagepicker.consts.SINGLE_PICKER_BOTTOM_SHEET
import com.crazylegend.imagepicker.consts.SINGLE_PICKER_DIALOG
import com.crazylegend.imagepicker.dialogs.MultiImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.dialogs.SingleImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.dialogs.SingleImagePickerDialogFragment
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.listeners.onImageDSL
import com.crazylegend.imagepicker.listeners.onImagesDSL


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
object MultiImagePicker {


    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun bottomSheetPicker(context: Context, imagesList:(list:List<ImageModel>)->Unit) {
        val manager = setupManager(context)
        val multiImagePickerBottomSheetDialog = MultiImagePickerBottomSheetDialog()
        multiImagePickerBottomSheetDialog.show(manager, SINGLE_PICKER_BOTTOM_SHEET)
        MultiImagePickerBottomSheetDialog.onImagesPicked = onImagesDSL {
            imagesList(it)
        }
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    fun dialogPicker(context: Context, onPickedImage: (image: ImageModel) -> Unit) {
        val manager = setupManager(context)
        val singleImagePickerBottomSheetDialog = SingleImagePickerDialogFragment()
        singleImagePickerBottomSheetDialog.show(manager, SINGLE_PICKER_DIALOG)
        SingleImagePickerBottomSheetDialog.onImagePicked = onImageDSL {
            onPickedImage(it)
        }
    }

    private fun setupManager(context: Context): FragmentManager {
        val manager = when (context) {
            is Fragment -> context.childFragmentManager
            is AppCompatActivity -> context.supportFragmentManager
            else -> null
        }
        requireNotNull(manager) {
            "Use a Fragment or AppCompat activity"
        }
        return manager
    }
}