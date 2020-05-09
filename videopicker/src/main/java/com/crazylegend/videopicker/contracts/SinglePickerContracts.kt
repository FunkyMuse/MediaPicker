package com.crazylegend.videopicker.contracts

import androidx.activity.result.ActivityResultLauncher
import com.crazylegend.videopicker.R
import com.crazylegend.videopicker.adapters.single.VideoAdapter
import com.crazylegend.videopicker.databinding.FragmentVideoGalleryLayoutBinding
import com.crazylegend.videopicker.listeners.onVideoPicked
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
    val errorTag : String get() = SingleVideoPicker::javaClass.name

}