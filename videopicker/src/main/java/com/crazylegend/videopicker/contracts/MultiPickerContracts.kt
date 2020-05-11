package com.crazylegend.videopicker.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.core.MODIFIER_ARGUMENT_CONST
import com.crazylegend.videopicker.R
import com.crazylegend.videopicker.adapters.multi.VideosMultiSelectAdapter
import com.crazylegend.videopicker.databinding.FragmentVideoGalleryLayoutMultiBinding
import com.crazylegend.videopicker.listeners.onVideosPicked
import com.crazylegend.videopicker.modifiers.multi.MultiVideoPickerModifier
import com.crazylegend.videopicker.pickers.MultiVideoPicker
import com.crazylegend.videopicker.videos.VideosVM
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface MultiPickerContracts {
    val videosVM: VideosVM
    val binding: FragmentVideoGalleryLayoutMultiBinding
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_video_gallery_layout_multi
    val videosMultiSelectAdapter: VideosMultiSelectAdapter
    var onVideosPicked: onVideosPicked?
    val errorTag : String get() = MultiVideoPicker::javaClass.name
    fun addModifier(modifier: MultiVideoPickerModifier)
    val modifier : MultiVideoPickerModifier?
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    fun applyDoneButtonModifications(doneButton: MaterialButton)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
}