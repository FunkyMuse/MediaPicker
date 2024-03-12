package com.crazylegend.imagepicker.pickers

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.crazylegend.core.dto.Config
import com.crazylegend.core.modifiers.base.BaseSinglePickerModifier
import com.crazylegend.core.setupModifier
import com.crazylegend.extensions.setupManager
import com.crazylegend.imagepicker.consts.SINGLE_PICKER_BOTTOM_SHEET
import com.crazylegend.imagepicker.consts.SINGLE_PICKER_DIALOG
import com.crazylegend.imagepicker.dialogs.single.SingleImagePickerBottomSheetDialog
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.listeners.onImageDSL


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
object SingleImagePicker {

    const val SINGLE_IMAGE_REQUEST_KEY = "singleImageRequest"
    const val ON_SINGLE_IMAGE_PICK_KEY = "onSingleImagePicked"

    fun restoreListener(context: Context, onPickedImage: (image: ImageModel) -> Unit = {}) {
        val manager = context.setupManager()
        when (val fragment = manager.findFragmentByTag(SINGLE_PICKER_BOTTOM_SHEET)
            ?: manager.findFragmentByTag(SINGLE_PICKER_DIALOG)) {
            is SingleImagePickerBottomSheetDialog -> {
                fragment.onImagePicked = onImageDSL(onPickedImage)
            }

            null -> {
                Log.e(SingleImagePicker::class.java.name, "FRAGMENT NOT FOUND")
            }
        }
    }

    fun showPicker(
            context: Context,
            extensions: Array<String>? = arrayOf(),
            config: Config = Config(),
            pickerModifier: BaseSinglePickerModifier.() -> Unit = {},
            onPickedImage: (image: ImageModel) -> Unit = {}
    ) {
        val modifier = setupModifier(pickerModifier)
        val manager = context.setupManager()
        with(SingleImagePickerBottomSheetDialog()) {
            this.extensions = extensions
            this.config = config
            addModifier(modifier)
            onImagePicked = onImageDSL(onPickedImage)
            show(manager, SINGLE_PICKER_BOTTOM_SHEET)
        }
    }

    fun showPicker(
        fragmentManager: FragmentManager,
        extensions: Array<String>? = arrayOf(),
        config: Config = Config(),
        pickerModifier: BaseSinglePickerModifier.() -> Unit = {},
        onPickedImage: (image: ImageModel) -> Unit = {}
    ) {
        val modifier = setupModifier(pickerModifier)
        with(SingleImagePickerBottomSheetDialog()) {
            this.extensions = extensions
            this.config = config
            addModifier(modifier)
            onImagePicked = onImageDSL(onPickedImage)
            show(fragmentManager, SINGLE_PICKER_BOTTOM_SHEET)
        }
    }


}