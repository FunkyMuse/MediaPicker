package com.crazylegend.core.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.core.R
import com.crazylegend.core.adapters.multi.MultiSelectAdapter
import com.crazylegend.core.consts.MODIFIER_ARGUMENT_CONST
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/11/20 to long live and prosper !
 */
interface BaseContractMultiPick {
    val multiSelectAdapter:MultiSelectAdapter
    fun addModifier(modifier: MultiPickerModifier)
    val modifier : MultiPickerModifier?
    val binding: FragmentImagesGalleryLayoutMultiBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_images_gallery_layout_multi
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    fun applyDoneButtonModifications(doneButton:MaterialButton)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
}