package com.crazylegend.core.abstracts

import android.content.res.Configuration
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.util.keyIterator
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.gone
import com.crazylegend.core.visible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView


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

    fun setupUIForSinglePicker(close: AppCompatImageView, gallery: RecyclerView, singleAdapter: RecyclerView.Adapter<*>,
                               title: MaterialTextView,
                               titleModifications: (MaterialTextView) -> Unit) {
        close.gone()
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = singleAdapter
        }
        titleModifications(title)
    }

    fun setupUIForMultiPicker(savedInstanceState: Bundle?,
                              LIST_STATE: String,
                              selectedPositions: SparseBooleanArray,
                              gallery: RecyclerView, multiSelectAdapter: RecyclerView.Adapter<*>,
                              doneButton: MaterialButton, title: MaterialTextView,
                              onDoneButton:(MaterialButton)->Unit, onTitleButton:(MaterialTextView)->Unit) {
        savedInstanceState?.getIntegerArrayList(LIST_STATE)?.asSequence()?.forEach { selectedPositions.put(it, true) }
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = multiSelectAdapter
        }
        onDoneButton(doneButton)
        onTitleButton(title)
    }

    fun handleUIIndicator(liveData: LiveData<Boolean>, loadingIndicator: ProgressBar) {
        liveData.observe(viewLifecycleOwner){
            if (it){
                loadingIndicator.visible()
            } else {
                loadingIndicator.gone()
            }
        }
    }
}