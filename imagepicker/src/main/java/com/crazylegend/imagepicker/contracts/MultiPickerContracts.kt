package com.crazylegend.imagepicker.contracts

import androidx.activity.result.ActivityResultLauncher
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.adapters.multi.ImagesMultiSelectAdapter
import com.crazylegend.imagepicker.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked
import com.crazylegend.imagepicker.pickers.MultiImagePicker


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
}