package com.crazylegend.core.abstracts

import android.content.res.Configuration
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.keyIterator
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

}