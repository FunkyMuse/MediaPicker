package com.crazylegend.core.abstracts

import android.app.Dialog
import android.os.Bundle
import android.util.SparseBooleanArray
import androidx.core.util.keyIterator
import androidx.fragment.app.DialogFragment
import com.crazylegend.core.R


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

    fun saveSelectedPositionsState(selectedPositions: SparseBooleanArray, outState: Bundle, LIST_STATE:String){
        val positionList = selectedPositions.keyIterator().asSequence().map { it }.toList()
        outState.putIntegerArrayList(LIST_STATE, ArrayList(positionList))
    }
}