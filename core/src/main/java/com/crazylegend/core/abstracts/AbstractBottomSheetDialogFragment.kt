package com.crazylegend.core.abstracts

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.R
import com.crazylegend.core.gone
import com.crazylegend.core.modifiers.TitleTextModifier
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


    fun setupUIForSinglePicker(gallery: RecyclerView, singleAdapter: RecyclerView.Adapter<*>,
                               title: MaterialTextView, loadingIndicator: ProgressBar, progressBarTint: Int?,
                               titleModifications: (MaterialTextView) -> Unit) {
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = singleAdapter
        }
        titleModifications(title)
        progressBarTint?.let { loadingIndicator.indeterminateDrawable.setTint(it) }
    }

    fun <T> setupList(adapter: ListAdapter<T, *>, list: List<T>, noContentText: MaterialTextView, noContentTextModifier: TitleTextModifier?) {
        if (list.isEmpty()) {
            adapter.submitList(emptyList())
            noContentText.visible()
            noContentTextModifier?.apply {
                textString = textString ?: getString(R.string.no_content_found)
                applyTextParams(noContentText)
            }
        } else {
            noContentText.gone()
            adapter.submitList(list)
        }
    }

    fun setupUIForMultiPicker(gallery: RecyclerView,
                              multiSelectAdapter: RecyclerView.Adapter<*>, doneButton: MaterialButton,
                              title: MaterialTextView, loadingIndicator: ProgressBar, progressBarTint: Int?, onDoneButton: (MaterialButton) -> Unit,
                              onTitleButton: (MaterialTextView) -> Unit) {
        gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = multiSelectAdapter
        }
        onDoneButton(doneButton)
        onTitleButton(title)
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
}