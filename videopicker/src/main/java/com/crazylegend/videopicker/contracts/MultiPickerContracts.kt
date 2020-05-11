package com.crazylegend.videopicker.contracts

import com.crazylegend.core.contracts.BaseContractMultiPick
import com.crazylegend.videopicker.listeners.onVideosPicked
import com.crazylegend.videopicker.pickers.MultiVideoPicker
import com.crazylegend.videopicker.videos.VideosVM


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface MultiPickerContracts :BaseContractMultiPick{
    val videosVM: VideosVM
    var onVideosPicked: onVideosPicked?
    val errorTag : String get() = MultiVideoPicker::javaClass.name
}