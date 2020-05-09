package com.crazylegend.core.abstracts

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.crazylegend.core.R
import com.crazylegend.core.setupDialogOnStart


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */

abstract class AbstractDialogFragment : DialogFragment() {
    abstract val layout: Int

    override fun onStart() {
        super.onStart()
        setupDialogOnStart()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        with(dialog) {
            window?.attributes?.windowAnimations = R.style.AbstractDialogAnimation
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawableResource(R.drawable.rounded_bg_abstract_dialog)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(layout, container, false)

}