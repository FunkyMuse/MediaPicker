package com.crazylegend.videopicker.contracts

import com.crazylegend.core.contracts.BaseContractSinglePick
import com.crazylegend.videopicker.listeners.onVideoPicked
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideosVM


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface SinglePickerContracts : BaseContractSinglePick {
    val videosVM: VideosVM
    var onVideoPicked: onVideoPicked?
    val errorTag get() = SingleVideoPicker::javaClass.name
}