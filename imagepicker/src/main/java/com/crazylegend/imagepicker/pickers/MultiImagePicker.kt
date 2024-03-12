package com.crazylegend.imagepicker.pickers

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.crazylegend.core.dto.Config
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.core.setupModifier
import com.crazylegend.extensions.setupManager
import com.crazylegend.imagepicker.consts.MULTI_PICKER_BOTTOM_SHEET
import com.crazylegend.imagepicker.consts.MULTI_PICKER_DIALOG
import com.crazylegend.imagepicker.dialogs.multi.MultiImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.listeners.onImagesDSL


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
object MultiImagePicker {


    const val MULTI_IMAGE_REQUEST_KEY = "multiImagesRequest"
    const val ON_MULTI_IMAGE_PICK_KEY = "onMultiImagesPicked"

    fun restoreListener(context: Context, imagesList: (list: List<ImageModel>) -> Unit = {}) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(MULTI_PICKER_BOTTOM_SHEET)
            ?: manager.findFragmentByTag(MULTI_PICKER_DIALOG)) {
            is MultiImagePickerBottomSheetDialog -> {
                fragment.onImagesPicked = onImagesDSL(imagesList)
            }

            null -> {
                Log.e(MultiImagePicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }


    fun showPicker(
        context: Context, extensions: Array<String>? = arrayOf(),
        config: Config = Config(),
        multiImagePickerModifier: BaseMultiPickerModifier.() -> Unit = {},
        imagesList: (list: List<ImageModel>) -> Unit = {}
    ) {
        val manager = context.setupManager()
        val modifier = setupModifier(multiImagePickerModifier)
        with(MultiImagePickerBottomSheetDialog()) {
            this.extensions = extensions
            this.config = config
            addModifier(modifier)
            onImagesPicked = onImagesDSL(imagesList)
            show(manager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }

    fun showPicker(
            fragmentManager: FragmentManager,
            extensions: Array<String>? = arrayOf(),
            config: Config = Config(),
            multiImagePickerModifier: BaseMultiPickerModifier.() -> Unit = {},
            imagesList: (list: List<ImageModel>) -> Unit = {}
    ) {
        val modifier = setupModifier(multiImagePickerModifier)
        with(MultiImagePickerBottomSheetDialog()) {
            this.extensions = extensions
            this.config = config
            addModifier(modifier)
            onImagesPicked = onImagesDSL(imagesList)
            show(fragmentManager, MULTI_PICKER_BOTTOM_SHEET)
        }
    }


}