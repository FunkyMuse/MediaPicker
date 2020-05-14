package com.crazylegend.core.abstracts

import android.app.Dialog
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.util.keyIterator
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

    inline fun <T> onValuesPicked(selectedPositions: SparseBooleanArray, loadedList: List<T>, onPickedList: (list: MutableList<T>) -> Unit) {
        val pickedList = mutableListOf<T>()
        selectedPositions.keyIterator().asSequence().forEach {
            pickedList += loadedList[it]
        }
        onPickedList(pickedList)
        dismissAllowingStateLoss()
    }

    fun saveSelectedPositionsState(selectedPositions: SparseBooleanArray, outState: Bundle, LIST_STATE: String) {
        val positionList = selectedPositions.keyIterator().asSequence().map { it }.toList()
        outState.putIntegerArrayList(LIST_STATE, ArrayList(positionList))
    }

    fun setupUIForSinglePicker(topIndicator: AppCompatImageView, gallery: RecyclerView, singleAdapter: RecyclerView.Adapter<*>,
                               title: MaterialTextView, close: AppCompatImageView,
                               titleModifications: (MaterialTextView) -> Unit, onCloseButton: (AppCompatImageView) -> Unit) {
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
                              savedInstanceState: Bundle?,
                              LIST_STATE: String,
                              selectedPositions: SparseBooleanArray,
                              gallery: RecyclerView, multiSelectAdapter: RecyclerView.Adapter<*>,
                              doneButton: MaterialButton, title: MaterialTextView,
                              onDoneButton: (MaterialButton) -> Unit, onTitleButton: (MaterialTextView) -> Unit) {
        topIndicator.gone()
        savedInstanceState?.getIntegerArrayList(LIST_STATE)?.asSequence()?.forEach { selectedPositions.put(it, true) }
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = multiSelectAdapter
        }
        onDoneButton(doneButton)
        onTitleButton(title)
    }
}