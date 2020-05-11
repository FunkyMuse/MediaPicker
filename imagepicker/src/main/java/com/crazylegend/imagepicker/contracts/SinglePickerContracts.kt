package com.crazylegend.imagepicker.contracts

import com.crazylegend.core.contracts.BaseContractSinglePick
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagePicked
import com.crazylegend.imagepicker.pickers.SingleImagePicker


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal interface SinglePickerContracts : BaseContractSinglePick {
    val imagesVM: ImagesVM
    var onImagePicked: onImagePicked?
    val errorTag get() = SingleImagePicker::javaClass.name
}