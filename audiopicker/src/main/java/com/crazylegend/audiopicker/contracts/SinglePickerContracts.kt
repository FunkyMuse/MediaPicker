package com.crazylegend.audiopicker.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.audiopicker.adapters.single.AudioSingleAdapter
import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.listeners.onAudioPicked
import com.crazylegend.audiopicker.modifiers.SingleAudioPickerModifier
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.R
import com.crazylegend.core.consts.MODIFIER_ARGUMENT_CONST
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutBinding


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */
internal interface SinglePickerContracts  {
    val audiosVM: AudiosVM
    var onAudioPicked: onAudioPicked?
    val errorTag: String get() = SingleAudioPicker::javaClass.name

    fun addModifier(modifier: SingleAudioPickerModifier)
    val modifier : SingleAudioPickerModifier?
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
    val binding: FragmentImagesGalleryLayoutBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_images_gallery_layout
    val singleAdapter: AudioSingleAdapter

    fun recycleBitmaps()

}