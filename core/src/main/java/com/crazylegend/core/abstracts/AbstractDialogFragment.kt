package com.crazylegend.core.abstracts

import android.app.Dialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.R
import com.crazylegend.core.gone
import com.crazylegend.core.visible
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView


/**
 * Created by crazy on 5/9/20 to long live and prosper !
 */

abstract class AbstractDialogFragment(contentLayoutId: Int) : DialogFragment(contentLayoutId) {

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



    fun setupUIForSinglePicker(topIndicator: AppCompatImageView, gallery: RecyclerView, singleAdapter: RecyclerView.Adapter<*>,
                               title: MaterialTextView,
                               close: AppCompatImageView, loadingIndicator: ProgressBar, progressBarTint: Int?, titleModifications: (MaterialTextView) -> Unit, onCloseButton: (AppCompatImageView) -> Unit) {
        topIndicator.gone()
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = singleAdapter
        }
        titleModifications(title)
        onCloseButton(close)
        close.setOnClickListener {
            dismissAllowingStateLoss()
        }
        progressBarTint?.let { loadingIndicator.indeterminateDrawable.setTint(it) }
    }

    fun handleUIIndicator(liveData: LiveData<Boolean>, loadingIndicator: ProgressBar) {
        liveData.observe(viewLifecycleOwner) {
            if (it) {
                loadingIndicator.visible()
            } else {
                loadingIndicator.gone()
            }
        }
    }

    fun setupUIForMultiPicker(topIndicator: AppCompatImageView,
                              gallery: RecyclerView,
                              multiSelectAdapter: RecyclerView.Adapter<*>,
                              doneButton: MaterialButton,
                              title: MaterialTextView, loadingIndicator: ProgressBar,
                              progressBarTint: Int?, onDoneButton: (MaterialButton) -> Unit, onTitleButton: (MaterialTextView) -> Unit) {
        topIndicator.gone()
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = multiSelectAdapter
        }
        onDoneButton(doneButton)
        onTitleButton(title)
        progressBarTint?.let { loadingIndicator.indeterminateDrawable.setTint(it) }

    }
}