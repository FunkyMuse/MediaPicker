package com.crazylegend.core.abstracts

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */
abstract class AbstractBottomSheetDialogFragment : BottomSheetDialogFragment() {
    abstract val layout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(layout, container, false)

    //work around when using DayNight to change the background color to light/night
    override fun onConfigurationChanged(overrideConfiguration: Configuration) {
        val uiMode = overrideConfiguration.uiMode
        overrideConfiguration.setTo(requireContext().resources.configuration)
        overrideConfiguration.uiMode = uiMode
        super.onConfigurationChanged(overrideConfiguration)
    }
}