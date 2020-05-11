package com.crazylegend.imagepicker.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.core.MODIFIER_ARGUMENT_CONST
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.adapters.multi.ImagesMultiSelectAdapter
import com.crazylegend.imagepicker.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked
import com.crazylegend.imagepicker.modifiers.multi.MultiImagePickerModifier
import com.crazylegend.imagepicker.pickers.MultiImagePicker
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface MultiPickerContracts {
    val imagesVM: ImagesVM
    val binding: FragmentImagesGalleryLayoutMultiBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_images_gallery_layout_multi
    val imagesAdapter: ImagesMultiSelectAdapter
    var onImagesPicked: onImagesPicked?
    val errorTag : String get() = MultiImagePicker::javaClass.name
    fun addModifier(modifier: MultiImagePickerModifier)
    val modifier : MultiImagePickerModifier?
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    fun applyDoneButtonModifications(doneButton:MaterialButton)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
}