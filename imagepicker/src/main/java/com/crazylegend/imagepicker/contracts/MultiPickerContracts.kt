package com.crazylegend.imagepicker.contracts

import com.crazylegend.core.contracts.BaseContractMultiPick
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked
import com.crazylegend.imagepicker.pickers.MultiImagePicker


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface MultiPickerContracts : BaseContractMultiPick {
    val imagesVM: ImagesVM
    var onImagesPicked: onImagesPicked?
    val errorTag : String get() = MultiImagePicker::javaClass.name

}