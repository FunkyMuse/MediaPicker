package com.crazylegend.core.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.core.R
import com.crazylegend.core.adapters.single.SingleAdapter
import com.crazylegend.core.consts.MODIFIER_ARGUMENT_CONST
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.core.modifiers.single.SinglePickerModifier


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */
interface BaseContractSinglePick {
    fun addModifier(modifier: SinglePickerModifier)
    val modifier: SinglePickerModifier?
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
    val binding: FragmentImagesGalleryLayoutBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_images_gallery_layout
    val singleAdapter: SingleAdapter

}