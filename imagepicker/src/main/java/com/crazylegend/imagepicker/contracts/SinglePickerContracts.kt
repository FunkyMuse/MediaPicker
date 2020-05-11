package com.crazylegend.imagepicker.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.core.MODIFIER_ARGUMENT_CONST
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.adapters.single.ImagesAdapter
import com.crazylegend.imagepicker.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagePicked
import com.crazylegend.imagepicker.modifiers.single.SingleImagePickerModifier
import com.crazylegend.imagepicker.pickers.SingleImagePicker


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface SinglePickerContracts {
    val imagesVM: ImagesVM
    val binding: FragmentImagesGalleryLayoutBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_images_gallery_layout
    val imagesAdapter: ImagesAdapter
    var onImagePicked: onImagePicked?
    val errorTag  get() = SingleImagePicker::javaClass.name
    fun addModifier(modifier: SingleImagePickerModifier)
    val modifier : SingleImagePickerModifier?
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
}