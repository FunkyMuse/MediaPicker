package com.crazylegend.imagepicker.contracts

import androidx.activity.result.ActivityResultLauncher
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.adapter.multi.ImagesMultiSelectAdapter
import com.crazylegend.imagepicker.databinding.FragmentGalleryLayoutMultiBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface MultiPickerContracts {
    val imagesVM: ImagesVM
    val binding: FragmentGalleryLayoutMultiBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_gallery_layout_multi
    val imagesAdapter: ImagesMultiSelectAdapter
    var onImagesPicked: onImagesPicked?
}