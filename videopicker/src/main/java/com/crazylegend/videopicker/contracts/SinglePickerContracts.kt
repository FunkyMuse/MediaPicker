package com.crazylegend.videopicker.contracts

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatTextView
import com.crazylegend.core.MODIFIER_ARGUMENT_CONST
import com.crazylegend.videopicker.R
import com.crazylegend.videopicker.adapters.single.VideoAdapter
import com.crazylegend.videopicker.databinding.FragmentVideoGalleryLayoutBinding
import com.crazylegend.videopicker.listeners.onVideoPicked
import com.crazylegend.videopicker.modifiers.SingleVideoPickerModifier
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideosVM


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface SinglePickerContracts {
    val videosVM: VideosVM
    val binding: FragmentVideoGalleryLayoutBinding?
    val askForStoragePermission: ActivityResultLauncher<String>
    val layout get() = R.layout.fragment_video_gallery_layout
    val videoAdapter: VideoAdapter
    var onVideoPicked: onVideoPicked?
    val errorTag  get() = SingleVideoPicker::javaClass.name
    fun addModifier(modifier: SingleVideoPickerModifier)
    val modifier :SingleVideoPickerModifier?
    fun applyTitleModifications(appCompatTextView: AppCompatTextView)
    val modifierTag get() = MODIFIER_ARGUMENT_CONST
}