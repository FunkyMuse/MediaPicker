package com.crazylegend.audiopicker.dialogs.multi

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.audiopicker.adapters.multi.AudioMultiSelectAdapter
import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.consts.LIST_STATE
import com.crazylegend.audiopicker.contracts.MultiPickerContracts
import com.crazylegend.audiopicker.listeners.onAudiosPicked
import com.crazylegend.core.abstracts.AbstractBottomSheetDialogFragment
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.extensions.viewBinding
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiAudioPickerBottomSheetDialog : AbstractBottomSheetDialogFragment(),
        MultiPickerContracts {


    override val layout: Int
        get() = super.layout
    override var onAudiosPicked: onAudiosPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutMultiBinding::bind)
    override val audiosVM by viewModels<AudiosVM>()
    override val modifier: MultiPickerModifier?
        get() = arguments?.getParcelable(modifierTag)
    override val multiSelectAdapter by lazy {
        AudioMultiSelectAdapter(modifier, lifecycleScope)
    }
    override val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    audiosVM.loadAudios()
                } else {
                    Log.e(errorTag, "PERMISSION DENIED")
                    dismissAllowingStateLoss()
                }
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.getIntegerArrayList(LIST_STATE)?.asSequence()?.forEach { multiSelectAdapter.selectedPositions.put(it, true) }
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = multiSelectAdapter
        }

        audiosVM.audio.observe(viewLifecycleOwner) {
            multiSelectAdapter.submitList(it)
        }

        applyDoneButtonModifications(binding.doneButton)
        applyTitleModifications(binding.title)

        binding.doneButton.setOnClickListener {
            onValuesPicked(multiSelectAdapter.selectedPositions, audiosVM.audio.value ?: emptyList()) { list ->
                onAudiosPicked?.forAudios(list)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveSelectedPositionsState(multiSelectAdapter.selectedPositions, outState, LIST_STATE)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycleBitmaps()
        onAudiosPicked = null
    }

    override fun addModifier(modifier: MultiPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }
    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun applyDoneButtonModifications(doneButton: MaterialButton) {
        modifier?.doneButtonModifier?.applyImageParams(doneButton)
    }

    override fun recycleBitmaps() {
        multiSelectAdapter.currentList.asSequence().forEach {
            it?.thumbnail?.apply {
                if (!isRecycled)
                    recycle()
            }
        }
    }
}